import { Stack, Text, Title } from "@mantine/core";
import CustomImage from "../../_components/CustomImage";

export default function Week1() {
  return (
    <>
      <Title>Week 1</Title>
      <Stack>
        <Text>
          Week 1 was spent teambuilding and finding each other’s strengths in
          preparation for the main planning phase. We also set up a vague
          outline for the process and orders necessary to complete the project.
          This can be seen below:
        </Text>
        <CustomImage src={"/images/wk1-1.png"} />
      </Stack>
    </>
  );
}
