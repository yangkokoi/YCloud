import { h } from "vue";
import { NIcon } from "naive-ui";
import { File, Folder, FileImage, FileCode, Splotch } from "@vicons/fa";

export const fileIcon = function(type = "FILE") {
  let icon = File;
  type = type.toUpperCase();
  if (type === "DIR") icon = Folder;
  else if (["JPG", "PNG", "JPEG"].includes(type)) icon = FileImage;
  else if (["txt", "js", "ts", "java", "md"].includes(type)) icon = FileCode;
  else if (["exe"].includes(type)) icon = Splotch;
  return h(
    NIcon,
    { size: 13, color: type === "DIR" ? "#E66E05" : "#333", class: "fileIcon" },
    () => h(icon)
  );
};
