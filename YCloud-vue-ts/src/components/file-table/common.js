import { h } from "vue";
import YIcon from "@/components/YIcon";

export const fileIconName = function(type = "FILE") {
  let icon = "file";
  type = type.toLowerCase();
  if (type === "dir") icon = "folder";
  else if (["jpg", "png", "jpeg"].includes(type)) icon = "file-image";
  else if (["txt", "js", "ts", "java", "md"].includes(type)) icon = "file-code";
  else if (["zip", "rar", "7z", "tar"].includes(type)) icon = "file-zipper";
  return "fa-" + icon;
};

export const fileIcon = function(type = "file", size = 13) {
  return h(
    YIcon,
    {
      name: fileIconName(type),
      size,
      color: type === "dir" ? "#E66E05" : "#333",
      class: "fileIcon"
    },
    null
  );
};
