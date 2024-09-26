// Import styles of packages that you've installed.
// All packages except `@mantine/hooks` require styles imports
import "@mantine/core/styles.css";

import { MantineProvider, Title } from "@mantine/core";
import { AppLayout } from "./_components/AppShell";

export default function App() {
  return (
    <MantineProvider defaultColorScheme="dark">
      <AppLayout>
        <Title>ENG1 Cohort 2 Group 3</Title>
      </AppLayout>
    </MantineProvider>
  );
}
