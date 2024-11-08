import { Stack, Text, Title } from "@mantine/core";
import CustomImage from "../../_components/CustomImage";

export default function Week4() {
  return (
    <>
      <Title>Week 4</Title>
      <Stack>
        <Text>
          We finalised our plan this week, adding more detail and finalising
          deadlines for each section of the project. The finalised plan can be
          seen below:
        </Text>
        <CustomImage src={"/images/wk4-1.png"} />
        <Text>
          We also updated the progress tracker to show what is done/what needs
          to be.
          <br />
          This will be added onto the end of each week from now on.
        </Text>
        <CustomImage src={"/images/wk4-2.png"} />
      </Stack>
    </>
  );
}
