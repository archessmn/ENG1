import { Card, Stack, Text, Title } from "@mantine/core";
import CustomImage from "../../_components/CustomImage";

export default function CRCCards() {
  return (
    <>
      <Title>CRC Cards</Title>
      <Stack>
        <Title order={3}>Classes:</Title>
        <Card>
          <Title order={3}>io.github.archessmn.ENG1.Main</Title>
          <Text>
            The main class that handles input, logic, and display for the whole
            game.
          </Text>
          <CustomImage src="images/crc/io.github.archessmn.ENG1.Main.png" />
        </Card>
        <Card>
          <Title order={3}>io.github.archessmn.ENG1.World</Title>
          <Text>Stores information about the world and buildings in it.</Text>
          <CustomImage src="images/crc/io.github.archessmn.ENG1.World.png" />
        </Card>
        <Card>
          <Title order={3}>io.github.archessmn.ENG1.Buildings.Building</Title>
          <Text>The main class used for defining buildings.</Text>
          <CustomImage src="images/crc/io.github.archessmn.ENG1.Buildings.Building.png" />
        </Card>
        <Card>
          <Title order={3}>
            io.github.archessmn.ENG1.Buildings.[Gym|Halls|LectureHall|Office|Piazza]Building
          </Title>
          <Text>
            Extended classes that specify the type of building to create.
          </Text>
          <CustomImage src="images/crc/io.github.archessmn.ENG1.Buildings.[Types].png" />
        </Card>
        <Title order={3}>Deprecated Classes:</Title>
        <Card>
          <Title order={3}>io.github.archessmn.ENG1.Building</Title>
          <Text>Deprecated class used to store building information</Text>
          <CustomImage src="images/crc/io.github.archessmn.ENG1.Building.png" />
        </Card>
        <Card>
          <Title order={3}>io.github.archessmn.ENG1.Inventory</Title>
          <Text>
            Deprecated class used to store the different building types and
            cycle through them.
          </Text>
          <CustomImage src="images/crc/io.github.archessmn.ENG1.Inventory.png" />
        </Card>
      </Stack>
    </>
  );
}
