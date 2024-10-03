import { NavLink, Title } from "@mantine/core";
import { NavLink as ReactNavLink } from "react-router-dom";

export function WeekList() {
  return (
    <>
      <Title>Weeks</Title>
      <NavLink
        label={"Week 1"}
        component={ReactNavLink}
        to="/weeks/1"
      />
    </>
  );
}
