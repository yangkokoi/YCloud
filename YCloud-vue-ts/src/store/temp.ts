import { defineStore } from 'pinia';
import { Resource } from "@/type/type";

//创建VueX对象
export const sharePinia = defineStore("temp", {
    state: () => {
        const state: {
            resource: Resource | null
        } = {
            resource: window.sessionStorage.getItem("resource") ? JSON.parse(window.sessionStorage.getItem("resource") as string) : null
        }
        return state;
    },
    actions: {
        setResource(resource: Resource) {
            this.resource = resource;
            window.sessionStorage.setItem("resource", JSON.stringify(resource));
        },
    }
})

export default sharePinia