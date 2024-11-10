import { NavLink } from "@mantine/core";
import { ReactNode } from "react";
import { NavLink as ReactNavLink } from "react-router-dom";
import { useLayoutContext } from "./LayoutContext";

export function CNavLink(props: {
  to: string;
  label: string;
  onClick?: () => void;
  leftSection?: ReactNode;
  rightSection?: ReactNode;
  children?: ReactNode;
  external?: boolean;
}) {
  const layout = useLayoutContext();

  return (
    <>
      <NavLink
        component={ReactNavLink}
        to={props.to}
        label={props.label}
        onClick={() => {
          if (props.onClick) {
            props.onClick();
          }
          layout.navbar.mobile.close();
        }}
        leftSection={props.leftSection}
        rightSection={props.rightSection}
        target={props.external ? "_blank" : ""}
        rel={props.external ? "noopener noreferrer" : ""}
      >
        {props.children}
      </NavLink>
    </>
  );
}
