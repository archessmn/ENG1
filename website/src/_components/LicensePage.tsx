import { useEffect, useState } from "react";
import coreLicenseDependency from "/core-license-dependency.json?url";
import lwjglLicenseDependency from "/lwjgl3-license-dependency.json?url";
import { Card, Stack, Text, Title } from "@mantine/core";

type TLicense = { name: string; url: string; dependencies: string[] };

export default function LicensePage() {
  const [data, setData] = useState<TLicense[]>();
  // const [data, setData] = useState("");

  useEffect(() => {
    fetch(coreLicenseDependency)
      .then((res) => res.json())
      .then((coreData) => {
        fetch(lwjglLicenseDependency)
          .then((res) => res.json())
          .then((lwjglData) => {
            const coreLicenses = (
              coreData as unknown as { licences: TLicense[] }
            ).licences;

            const lwjglLicenses = (
              lwjglData as unknown as { licences: TLicense[] }
            ).licences;

            const combinedLicenses: TLicense[] = [];

            for (const license of lwjglLicenses) {
              if (coreLicenses.find((lcs) => lcs.name == license.name)) {
                const licenseDependencies = (
                  [
                    ...new Set(
                      coreLicenses[
                        coreLicenses.findIndex(
                          (lcs) => (lcs.name = license.name)
                        )
                      ].dependencies.concat(license.dependencies)
                    ),
                  ] as string[]
                ).sort((a, b) => (a > b ? 1 : -1));

                combinedLicenses.push({
                  ...license,
                  dependencies: licenseDependencies,
                });
              } else {
                combinedLicenses.push({
                  ...license,
                  dependencies: license.dependencies.sort((a, b) =>
                    a > b ? 1 : -1
                  ),
                });
              }
            }

            setData(
              combinedLicenses.sort((a, b) => (a.name > b.name ? 1 : -1))
            );
          });
      });
  }, []);

  console.log(data);
  // console.log(lwjglLicenses);
  return (
    <>
      {data == undefined ? (
        <>Oops</>
      ) : (
        <>
          <Stack>
            {data.map((license) => {
              return (
                <Card>
                  <Stack gap={0}>
                    <Title order={2}>{license.name}</Title>
                    {license.dependencies.map((dependency) => (
                      <Text>{dependency}</Text>
                    ))}
                  </Stack>
                </Card>
              );
            })}
          </Stack>
        </>
      )}
    </>
  );
}
