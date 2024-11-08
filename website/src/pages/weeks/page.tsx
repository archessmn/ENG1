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
      <NavLink
        label={"Week 2"}
        component={ReactNavLink}
        to="/weeks/2"
      />
      <NavLink
        label={"Week 3"}
        component={ReactNavLink}
        to="/weeks/3"
      />
      <NavLink
        label={"Week 4"}
        component={ReactNavLink}
        to="/weeks/4"
      />
      <NavLink
        label={"Week 5"}
        component={ReactNavLink}
        to="/weeks/5"
      />
      <NavLink
        label={"Week 6"}
        component={ReactNavLink}
        to="/weeks/6"
      />
      <NavLink
        label={"Week 7"}
        component={ReactNavLink}
        to="/weeks/7"
      />
    </>
  );
}
