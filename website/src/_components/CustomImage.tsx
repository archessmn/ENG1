import { Image } from "@mantine/core";
import { modals } from "@mantine/modals";

export default function CustomImage(props: { src: string }) {
  return (
    <Image
      src={props.src}
      maw={500}
      mah={600}
      fit="contain"
      onClick={() => {
        modals.open({
          title: `Image: ${props.src}`,
          fullScreen: true,
          children: (
            <Image
              src={props.src}
              style={{
                maxHeight: "calc(100vh - 100px)",
              }}
              fit="contain"
            />
          ),
        });
      }}
      style={{
        cursor: "zoom-in",
      }}
    />
  );
}
