import { Stack, Text, Title } from "@mantine/core";
import CustomImage from "../../_components/CustomImage";

export default function Week3() {
  return (
    <>
      <Title>Week 3</Title>
      <Stack>
        <Text>
          We set up more realistic time demands for our plan this week, given
          the need for a customer meeting. This can be seen below:
        </Text>
        <CustomImage src={"/images/wk3-1.png"} />
        <Text>
          Despite the idea of this chart being a high-level plan for the
          progression of this project, we plan to break this plan down slightly
          more later on, in order to have a few more development checkpoints.
          <br />
          We also set up a progress tracker with a Work Breakdown Structure
          chart, shown below:
        </Text>
        <CustomImage src={"/images/wk3-2.png"} />
        <Text>
          *Implementation has its own flow planning and is too large to include
          in this diagram.
          <br />
          We also created some section specific graphs to break these processes
          down a little more, allowing for more checkpoints and thus more
          direction with the development of these sections. This excludes the
          implementation section, as it follows the SCRUM cycle style explained
          in the main document. These flowcharts are shown below:
        </Text>
        <CustomImage src={"/images/wk3-3.png"} />
        <CustomImage src={"/images/wk3-4.png"} />
        <CustomImage src={"/images/wk3-5.png"} />
        <CustomImage src={"/images/wk3-6.png"} />
        <CustomImage src={"/images/wk3-7.png"} />
      </Stack>
    </>
  );
}
