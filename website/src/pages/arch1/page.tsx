import { NavLink, Title } from "@mantine/core";
import { NavLink as ReactNavLink } from "react-router-dom";

export default function Arch1Page() {
  return (
    <>
      <Title>Arch1</Title>
      <NavLink
        label={"CRC Cards"}
        component={ReactNavLink}
        to="/arch1/crc-cards"
      />
      <NavLink
        label={"Behavioral Diagrams"}
        component={ReactNavLink}
        to="/arch1/behavioral"
      />
      <NavLink
        label={"Structural Diagrams"}
        component={ReactNavLink}
        to="/arch1/structural"
      />
    </>
  );
}
