import { Card, Stack } from "@mantine/core";
import Week1 from "./weeks/Week1";
import Week2 from "./weeks/Week2";
import Week3 from "./weeks/Week3";
import Week4 from "./weeks/Week4";
import Week5 from "./weeks/Week5";
import Week6 from "./weeks/Week6";
import Week7 from "./weeks/Week7";
import { Link } from "react-router-dom";

export function RootPage() {
  const weeks = [Week1, Week2, Week3, Week4, Week5, Week6, Week7];

  return (
    <>
      <Stack>
        {weeks.map((Week, index) => {
          return (
            <Card mah={250}>
              <div
                style={{
                  position: "absolute",
                  height: 50,
                  width: "100%",
                  bottom: 0,
                  verticalAlign: "middle",
                  backgroundImage:
                    "linear-gradient(#00000000, #2e2e2eff, #2e2e2eff)",
                }}
              >
                <Link
                  to={`/weeks/${index + 1}`}
                  style={{
                    position: "absolute",
                    top: "50%",
                    left: "50%",
                    transform: "translate(-50%, -50%)",
                  }}
                >
                  See more...
                </Link>
              </div>
              <Week></Week>
            </Card>
          );
        })}
      </Stack>
    </>
  );
}
