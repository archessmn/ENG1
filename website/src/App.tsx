// Import styles of packages that you've installed.
// All packages except `@mantine/hooks` require styles imports
import "@mantine/core/styles.css";

import { MantineProvider, Title } from "@mantine/core";
import { AppLayout } from "./_components/AppShell";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { WeekList } from "./pages/weeks/page";
import Week1 from "./pages/weeks/Week1";
import LicensePage from "./_components/LicensePage";

export default function App() {
  return (
    <MantineProvider defaultColorScheme="dark">
      <BrowserRouter>
        <Routes>
          <Route
            path="/"
            element={<AppLayout />}
          >
            <Route
              path=""
              element={<Title>Hello World!</Title>}
            />
            <Route
              path="test"
              element={<Title>Test Page</Title>}
            />
            <Route
              path="licenses"
              element={<LicensePage />}
            />
            <Route path="weeks">
              <Route
                index
                element={<WeekList />}
              />
              <Route
                path="1"
                element={<Week1 />}
              />
            </Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </MantineProvider>
  );
}
