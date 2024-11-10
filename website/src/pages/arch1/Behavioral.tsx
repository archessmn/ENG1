import { Stack, Title } from "@mantine/core";
import CustomImage from "../../_components/CustomImage";

export default function BehavioralPage() {
  return (
    <>
      <Title>Behavioral</Title>
      <Stack>
        <Title order={2}>Initial Diagram:</Title>
        <CustomImage src="images/initial-behavioral.png" />
        <Title order={2}>Final Diagram:</Title>
        <CustomImage src="images/behavioral.png" />
      </Stack>
    </>
  );
}
