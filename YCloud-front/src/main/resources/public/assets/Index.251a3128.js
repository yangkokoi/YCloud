import{_ as z}from"./_plugin-vue_export-helper.cdc0426e.js";import{c as k,a as V,s as N,b as I,d as y,e as C,f as S,u as v,r as T,g as R,p as F,h as D,i as m,j as w,k as b,N as M,l as H,o as p,m as W,n as K,q as x,t as X,v as $,w as _,x as g,y as q,z as A}from"./index.5d1b28f5.js";import{u as Y}from"./use-message.c54d7482.js";import{u as G}from"./composables.7c04918d.js";const J=t=>{const{baseColor:e,textColor2:r,bodyColor:s,cardColor:l,dividerColor:o,actionColor:f,scrollbarColor:c,scrollbarColorHover:n,invertedColor:d}=t;return{textColor:r,textColorInverted:"#FFF",color:s,colorEmbedded:f,headerColor:l,headerColorInverted:d,footerColor:f,footerColorInverted:d,headerBorderColor:o,headerBorderColorInverted:d,footerBorderColor:o,footerBorderColorInverted:d,siderBorderColor:o,siderBorderColorInverted:d,siderColor:l,siderColorInverted:d,siderToggleButtonBorder:`1px solid ${o}`,siderToggleButtonColor:e,siderToggleButtonIconColor:r,siderToggleButtonIconColorInverted:r,siderToggleBarColor:I(s,c),siderToggleBarColorHover:I(s,n),__invertScrollbar:"true"}},Q=k({name:"Layout",common:V,peers:{Scrollbar:N},self:J}),P=Q,L={type:String,default:"static"},U=y("layout",`
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
`,[y("layout-scroll-container",`
 overflow-x: hidden;
 box-sizing: border-box;
 height: 100%;
 `),C("absolute-positioned",`
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 `)]),Z={embedded:Boolean,position:L,nativeScrollbar:{type:Boolean,default:!0},scrollbarProps:Object,onScroll:Function,contentStyle:{type:[String,Object],default:""},hasSider:Boolean,siderPlacement:{type:String,default:"left"}},ee=H("n-layout");function oe(t){return S({name:t?"LayoutContent":"Layout",props:Object.assign(Object.assign({},v.props),Z),setup(e){const r=T(null),s=T(null),{mergedClsPrefixRef:l,inlineThemeDisabled:o}=R(e),f=v("Layout","-layout",U,P,e,l);function c(a,i){if(e.nativeScrollbar){const{value:u}=r;u&&(i===void 0?u.scrollTo(a):u.scrollTo(a,i))}else{const{value:u}=s;u&&u.scrollTo(a,i)}}F(ee,e);let n=0,d=0;const O=a=>{var i;const u=a.target;n=u.scrollLeft,d=u.scrollTop,(i=e.onScroll)===null||i===void 0||i.call(e,a)};D(()=>{if(e.nativeScrollbar){const a=r.value;a&&(a.scrollTop=d,a.scrollLeft=n)}});const j={display:"flex",flexWrap:"nowrap",width:"100%",flexDirection:"row"},E={scrollTo:c},B=m(()=>{const{common:{cubicBezierEaseInOut:a},self:i}=f.value;return{"--n-bezier":a,"--n-color":e.embedded?i.colorEmbedded:i.color,"--n-text-color":i.textColor}}),h=o?w("layout",m(()=>e.embedded?"e":""),B,e):void 0;return Object.assign({mergedClsPrefix:l,scrollableElRef:r,scrollbarInstRef:s,hasSiderStyle:j,mergedTheme:f,handleNativeElScroll:O,cssVars:o?void 0:B,themeClass:h==null?void 0:h.themeClass,onRender:h==null?void 0:h.onRender},E)},render(){var e;const{mergedClsPrefix:r,hasSider:s}=this;(e=this.onRender)===null||e===void 0||e.call(this);const l=s?this.hasSiderStyle:void 0,o=[this.themeClass,t&&`${r}-layout-content`,`${r}-layout`,`${r}-layout--${this.position}-positioned`];return b("div",{class:o,style:this.cssVars},this.nativeScrollbar?b("div",{ref:"scrollableElRef",class:`${r}-layout-scroll-container`,style:[this.contentStyle,l],onScroll:this.handleNativeElScroll},this.$slots):b(M,Object.assign({},this.scrollbarProps,{onScroll:this.onScroll,ref:"scrollbarInstRef",theme:this.mergedTheme.peers.Scrollbar,themeOverrides:this.mergedTheme.peerOverrides.Scrollbar,contentStyle:[this.contentStyle,l]}),this.$slots))}})}const te=oe(!1),re=y("layout-footer",`
 transition:
 box-shadow .3s var(--n-bezier),
 color .3s var(--n-bezier),
 background-color .3s var(--n-bezier),
 border-color .3s var(--n-bezier);
 color: var(--n-text-color);
 background-color: var(--n-color);
 box-sizing: border-box;
`,[C("absolute-positioned",`
 position: absolute;
 left: 0;
 right: 0;
 bottom: 0;
 `),C("bordered",`
 border-top: solid 1px var(--n-border-color);
 `)]),le=Object.assign(Object.assign({},v.props),{inverted:Boolean,position:L,bordered:Boolean}),se=S({name:"LayoutFooter",props:le,setup(t){const{mergedClsPrefixRef:e,inlineThemeDisabled:r}=R(t),s=v("Layout","-layout-footer",re,P,t,e),l=m(()=>{const{common:{cubicBezierEaseInOut:f},self:c}=s.value,n={"--n-bezier":f};return t.inverted?(n["--n-color"]=c.footerColorInverted,n["--n-text-color"]=c.textColorInverted,n["--n-border-color"]=c.footerBorderColorInverted):(n["--n-color"]=c.footerColor,n["--n-text-color"]=c.textColor,n["--n-border-color"]=c.footerBorderColor),n}),o=r?w("layout-footer",m(()=>t.inverted?"a":"b"),l,t):void 0;return{mergedClsPrefix:e,cssVars:r?void 0:l,themeClass:o==null?void 0:o.themeClass,onRender:o==null?void 0:o.onRender}},render(){var t;const{mergedClsPrefix:e}=this;return(t=this.onRender)===null||t===void 0||t.call(this),b("div",{class:[`${e}-layout-footer`,this.themeClass,this.position&&`${e}-layout-footer--${this.position}-positioned`,this.bordered&&`${e}-layout-footer--bordered`],style:this.cssVars},this.$slots)}}),ne={},ae={style:{"font-size":"12px"}},ie=K(" \xA92020 - 2022 Power by WWW.XIAOSM.CN "),ce=x("br",null,null,-1),de=x("a",{target:"_blank",rel:"noopener",href:"https://beian.miit.gov.cn/"},[x("span",null,"\u6E58ICP\u590718023752\u53F7-1")],-1),ue=[ie,ce,de];function fe(t,e){return p(),W("div",ae,ue)}const he=z(ne,[["render",fe]]),be=S({__name:"Index",setup(t){return window.$message=Y(),window.$dialog=G(),X(()=>{console.log()}),(e,r)=>{const s=A("router-view"),l=te,o=se;return p(),$(l,{"content-style":"display: flex;flex-direction: column"},{default:_(()=>[g(l,{id:"main",style:{padding:"20px"}},{default:_(()=>[g(s,{style:{height:"100%"}})]),_:1}),g(o,{bordered:"",style:{height:"60px",display:"flex","align-items":"center","justify-content":"center"}},{default:_(()=>[(p(),$(q(he)))]),_:1})]),_:1})}}});const ye=z(be,[["__scopeId","data-v-320a6d15"]]);export{ye as default};
