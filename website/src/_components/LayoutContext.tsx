import { createContext, useContext } from "react";

interface TLayoutContext {
  navbar: {
    mobile: {
      opened: boolean;
      toggle: () => void;
      close: () => void;
    };
    desktop: {
      opened: boolean;
      toggle: () => void;
    };
  };
}

export const LayoutContext = createContext<TLayoutContext>(
  null as unknown as TLayoutContext
);

export const useLayoutContext = () => useContext(LayoutContext);
