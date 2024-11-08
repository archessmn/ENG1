import { Stack, Text, Title } from "@mantine/core";
import CustomImage from "../../_components/CustomImage";

export default function Week2() {
  return (
    <>
      <Title>Week 2</Title>
      <Stack>
        <Text>
          This week got together our first full gantt chart plan for the
          progress of this project. This is shown below:
        </Text>
        <CustomImage src={"/images/wk2-1.png"} />
        <Text>
          This chart will need improvement next week, as we are waiting on the
          customer meeting to start some of the requirements and implementation.
          <br />
          We also divided our workloads in order to share the marks equally
          amongst us. The chart depicting this can be seen below:
        </Text>
        <CustomImage src={"/images/wk2-2.png"} />
      </Stack>
    </>
  );
}
