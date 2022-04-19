import{e as p,f as x,S as v,i as C,j as B,o as S,d as I,a as y,r as g,u as T,p as j,c as R,k as L,h as f}from"./index.d1cb4c80.js";import{s as O,N as $}from"./Dropdown.8a0f4a2d.js";const z=u=>{const{baseColor:e,textColor2:o,bodyColor:t,cardColor:l,dividerColor:r,actionColor:c,scrollbarColor:h,scrollbarColorHover:b,invertedColor:s}=u;return{textColor:o,textColorInverted:"#FFF",color:t,colorEmbedded:c,headerColor:l,headerColorInverted:s,footerColor:c,footerColorInverted:s,headerBorderColor:r,headerBorderColorInverted:s,footerBorderColor:r,footerBorderColorInverted:s,siderBorderColor:r,siderBorderColorInverted:s,siderColor:l,siderColorInverted:s,siderToggleButtonBorder:`1px solid ${r}`,siderToggleButtonColor:e,siderToggleButtonIconColor:o,siderToggleButtonIconColorInverted:o,siderToggleBarColor:v(t,h),siderToggleBarColorHover:v(t,b),__invertScrollbar:"true"}},P=p({name:"Layout",common:x,peers:{Scrollbar:O},self:z});var _=P,w=C("layout",`
 color: var(--n-text-color);
 background-color: var(--n-color);
 box-sizing: border-box;
 position: relative;
 z-index: auto;
 flex: auto;
 overflow: hidden;
 transition:
 box-shadow .3s var(--n-bezier),
 background-color .3s var(--n-bezier),
 color .3s var(--n-bezier);
`,[C("layout-scroll-container",`
 overflow-x: hidden;
 box-sizing: border-box;
 height: 100%;
 `),B("absolute-positioned",`
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 `)]);const D=S("n-layout-sider"),E={type:String,default:"static"},F={embedded:Boolean,position:E,nativeScrollbar:{type:Boolean,default:!0},scrollbarProps:Object,onScroll:Function,contentStyle:{type:[String,Object],default:""},hasSider:Boolean,siderPlacement:{type:String,default:"left"}},k=S("n-layout");function H(u){return I({name:u?"LayoutContent":"Layout",props:Object.assign(Object.assign({},y.props),F),setup(e){const o=g(null),t=g(null),{mergedClsPrefixRef:l,inlineThemeDisabled:r}=T(e),c=y("Layout","-layout",w,_,e,l);function h(d,n){if(e.nativeScrollbar){const{value:i}=o;i&&(n===void 0?i.scrollTo(d):i.scrollTo(d,n))}else{const{value:i}=t;i&&i.scrollTo(d,n)}}j(k,e);const b={display:"flex",flexWrap:"nowrap",width:"100%",flexDirection:"row"},s={scrollTo:h},m=R(()=>{const{common:{cubicBezierEaseInOut:d},self:n}=c.value;return{"--n-bezier":d,"--n-color":e.embedded?n.colorEmbedded:n.color,"--n-text-color":n.textColor}}),a=r?L("layout",void 0,m,e):void 0;return Object.assign({mergedClsPrefix:l,scrollableElRef:o,scrollbarInstRef:t,hasSiderStyle:b,mergedTheme:c,cssVars:r?void 0:m,themeClass:a==null?void 0:a.themeClass,onRender:a==null?void 0:a.onRender},s)},render(){var e;const{mergedClsPrefix:o,hasSider:t}=this;(e=this.onRender)===null||e===void 0||e.call(this);const l=t?this.hasSiderStyle:void 0,r=[this.themeClass,u&&`${o}-layout-content`,`${o}-layout`,`${o}-layout--${this.position}-positioned`];return f("div",{class:r,style:this.cssVars},this.nativeScrollbar?f("div",{ref:"scrollableElRef",class:`${o}-layout-scroll-container`,style:[this.contentStyle,l],onScroll:this.onScroll},this.$slots):f($,Object.assign({},this.scrollbarProps,{onScroll:this.onScroll,ref:"scrollbarInstRef",theme:this.mergedTheme.peers.Scrollbar,themeOverrides:this.mergedTheme.peerOverrides.Scrollbar,contentStyle:[this.contentStyle,l]}),this.$slots))}})}var M=H(!1);export{M as _,k as a,D as b,_ as l,E as p};
