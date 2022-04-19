import{d as N,h as c,u as X,a as L,c as g,b as Re,e as Pe,f as Te,g as J,i as s,j as z,k as re,l as u,m as b,N as ge,n as O,r as j,t as Y,p as D,o as ne,q as V,F as Ne,s as fe,v as ke,w as ue,x as $e,y as me,z as oe,A as Me,B as Be,C as W,D as Ee,E as Oe,G as ve,H as F,I as E,J as Le,K as he,L as Fe,M as je,O as Ke,R as Z}from"./index.b56e0f28.js";import{_ as be}from"./plugin-vue_export-helper.21dcd24c.js";import{u as Ve}from"./use-message.d70c90f7.js";import{u as De,c as $}from"./use-rtl.e209f69a.js";import{f as Ue,t as We,a as Q,u as te,N as qe,k as ee,b as Ge}from"./Icon.b642b959.js";import{g as Ye,a as Xe,d as pe,b as Je,C as Ze,N as Qe,_ as eo,c as oo}from"./Dropdown.8ca8a6e4.js";import{l as xe,p as Ce,a as to,b as ye,_ as ro}from"./Layout.a6932eda.js";function ie(e){return Object.keys(e)}var no=N({name:"ChevronDownFilled",render(){return c("svg",{viewBox:"0 0 16 16",fill:"none",xmlns:"http://www.w3.org/2000/svg"},c("path",{d:"M3.20041 5.73966C3.48226 5.43613 3.95681 5.41856 4.26034 5.70041L8 9.22652L11.7397 5.70041C12.0432 5.41856 12.5177 5.43613 12.7996 5.73966C13.0815 6.0432 13.0639 6.51775 12.7603 6.7996L8.51034 10.7996C8.22258 11.0668 7.77743 11.0668 7.48967 10.7996L3.23966 6.7996C2.93613 6.51775 2.91856 6.0432 3.20041 5.73966Z",fill:"currentColor"}))}}),io={gapSmall:"4px 8px",gapMedium:"8px 12px",gapLarge:"12px 16px"};const lo=()=>io,ao={name:"Space",self:lo};var co=ao;const so=Object.assign(Object.assign({},L.props),{align:String,justify:{type:String,default:"start"},inline:Boolean,vertical:Boolean,size:{type:[String,Number,Array],default:"medium"},itemStyle:[String,Object],wrap:{type:Boolean,default:!0}});var uo=N({name:"Space",props:so,setup(e){const{mergedClsPrefixRef:t,mergedRtlRef:o}=X(e),n=L("Space","-space",void 0,co,e,t);return{rtlEnabled:De("Space",o,t),mergedClsPrefix:t,margin:g(()=>{const{size:l}=e;if(Array.isArray(l))return{horizontal:l[0],vertical:l[1]};if(typeof l=="number")return{horizontal:l,vertical:l};const{self:{[Re("gap",l)]:m}}=n.value,{row:v,col:d}=Xe(m);return{horizontal:pe(d),vertical:pe(v)}})}},render(){const{vertical:e,align:t,inline:o,justify:n,itemStyle:a,margin:l,wrap:m,mergedClsPrefix:v,rtlEnabled:d}=this,x=Ue(Ye(this));if(!x.length)return null;const R=`${l.horizontal}px`,f=`${l.horizontal/2}px`,p=`${l.vertical}px`,I=`${l.vertical/2}px`,_=x.length-1,A=n.startsWith("space-");return c("div",{role:"none",class:[`${v}-space`,d&&`${v}-space--rtl`],style:{display:o?"inline-flex":"flex",flexDirection:e?"column":"row",justifyContent:["start","end"].includes(n)?"flex-"+n:n,flexWrap:!m||e?"nowrap":"wrap",marginTop:e?"":`-${I}`,marginBottom:e?"":`-${I}`,alignItems:t}},x.map((k,w)=>c("div",{role:"none",style:[a,{maxWidth:"100%"},e?{marginBottom:w!==_?p:""}:d?{marginLeft:A?n==="space-between"&&w===_?"":f:w!==_?R:"",marginRight:A?n==="space-between"&&w===0?"":f:"",paddingTop:I,paddingBottom:I}:{marginRight:A?n==="space-between"&&w===_?"":f:w!==_?R:"",marginLeft:A?n==="space-between"&&w===0?"":f:"",paddingTop:I,paddingBottom:I}]},k)))}});function mo(e,t,o,n){return{itemColorHoverInverted:"#0000",itemColorActiveInverted:t,itemColorActiveHoverInverted:t,itemColorActiveCollapsedInverted:t,itemTextColorInverted:e,itemTextColorHoverInverted:o,itemTextColorChildActiveInverted:o,itemTextColorActiveInverted:o,itemTextColorActiveHoverInverted:o,itemTextColorHorizontalInverted:e,itemTextColorHoverHorizontalInverted:o,itemTextColorChildActiveHorizontalInverted:o,itemTextColorActiveHorizontalInverted:o,itemTextColorActiveHoverHorizontalInverted:o,itemIconColorInverted:e,itemIconColorHoverInverted:o,itemIconColorActiveInverted:o,itemIconColorActiveHoverInverted:o,itemIconColorChildActiveInverted:o,itemIconColorCollapsedInverted:e,itemIconColorHorizontalInverted:e,itemIconColorHoverHorizontalInverted:o,itemIconColorActiveHorizontalInverted:o,itemIconColorActiveHoverHorizontalInverted:o,itemIconColorChildActiveHorizontalInverted:o,arrowColorInverted:e,arrowColorHoverInverted:o,arrowColorActiveInverted:o,arrowColorActiveHoverInverted:o,arrowColorChildActiveInverted:o,groupTextColorInverted:n}}const vo=e=>{const{borderRadius:t,textColor3:o,primaryColor:n,textColor2:a,textColor1:l,fontSize:m,dividerColor:v,hoverColor:d,primaryColorHover:x}=e;return Object.assign({borderRadius:t,color:"#0000",groupTextColor:o,itemColorHover:d,itemColorActive:J(n,{alpha:.1}),itemColorActiveHover:J(n,{alpha:.1}),itemColorActiveCollapsed:J(n,{alpha:.1}),itemTextColor:a,itemTextColorHover:a,itemTextColorActive:n,itemTextColorActiveHover:n,itemTextColorChildActive:n,itemTextColorHorizontal:a,itemTextColorHoverHorizontal:x,itemTextColorActiveHorizontal:n,itemTextColorActiveHoverHorizontal:n,itemTextColorChildActiveHorizontal:n,itemIconColor:l,itemIconColorHover:l,itemIconColorActive:n,itemIconColorActiveHover:n,itemIconColorChildActive:n,itemIconColorCollapsed:l,itemIconColorHorizontal:l,itemIconColorHoverHorizontal:x,itemIconColorActiveHorizontal:n,itemIconColorActiveHoverHorizontal:n,itemIconColorChildActiveHorizontal:n,itemHeight:"42px",arrowColor:a,arrowColorHover:a,arrowColorActive:n,arrowColorActiveHover:n,arrowColorChildActive:n,colorInverted:"#0000",borderColorHorizontal:"#0000",fontSize:m,dividerColor:v},mo("#BBB",n,"#FFF","#AAA"))},ho=Pe({name:"Menu",common:Te,peers:{Tooltip:We,Dropdown:Je},self:vo});var po=ho,go=s("layout-footer",`
 transition:
 box-shadow .3s var(--n-bezier),
 color .3s var(--n-bezier),
 background-color .3s var(--n-bezier),
 border-color .3s var(--n-bezier);
 color: var(--n-text-color);
 background-color: var(--n-color);
 box-sizing: border-box;
`,[z("absolute-positioned",`
 position: absolute;
 left: 0;
 right: 0;
 bottom: 0;
 `),z("bordered",`
 border-top: solid 1px var(--n-border-color);
 `)]);const fo=Object.assign(Object.assign({},L.props),{inverted:Boolean,position:Ce,bordered:Boolean});var bo=N({name:"LayoutFooter",props:fo,setup(e){const{mergedClsPrefixRef:t,inlineThemeDisabled:o}=X(e),n=L("Layout","-layout-footer",go,xe,e,t),a=g(()=>{const{common:{cubicBezierEaseInOut:m},self:v}=n.value,d={"--n-bezier":m};return e.inverted?(d["--n-color"]=v.footerColorInverted,d["--n-text-color"]=v.textColorInverted,d["--n-border-color"]=v.footerBorderColorInverted):(d["--n-color"]=v.footerColor,d["--n-text-color"]=v.textColor,d["--n-border-color"]=v.footerBorderColor),d}),l=o?re("layout-footer",g(()=>e.inverted?"a":"b"),a,e):void 0;return{mergedClsPrefix:t,cssVars:o?void 0:a,themeClass:l==null?void 0:l.themeClass,onRender:l==null?void 0:l.onRender}},render(){var e;const{mergedClsPrefix:t}=this;return(e=this.onRender)===null||e===void 0||e.call(this),c("div",{class:[`${t}-layout-footer`,this.themeClass,this.position&&`${t}-layout-footer--${this.position}-positioned`,this.bordered&&`${t}-layout-footer--bordered`],style:this.cssVars},this.$slots)}}),xo=s("layout-sider",`
 flex-shrink: 0;
 box-sizing: border-box;
 position: relative;
 z-index: 1;
 color: var(--n-text-color);
 transition:
 color .3s var(--n-bezier),
 border-color .3s var(--n-bezier),
 min-width .3s var(--n-bezier),
 max-width .3s var(--n-bezier),
 transform .3s var(--n-bezier),
 background-color .3s var(--n-bezier);
 background-color: var(--n-color);
 display: flex;
 justify-content: flex-end;
`,[z("bordered",[u("border",`
 content: "";
 position: absolute;
 top: 0;
 bottom: 0;
 width: 1px;
 background-color: var(--n-border-color);
 transition: background-color .3s var(--n-bezier);
 `)]),u("left-placement",[z("bordered",[u("border",`
 right: 0;
 `)])]),z("right-placement",`
 justify-content: flex-start;
 `,[z("bordered",[u("border",`
 left: 0;
 `)]),z("collapsed",[s("layout-toggle-button",[s("base-icon",`
 transform: rotate(180deg);
 `)]),s("layout-toggle-bar",[b("&:hover",[u("top",{transform:"rotate(-12deg) scale(1.15) translateY(-2px)"}),u("bottom",{transform:"rotate(12deg) scale(1.15) translateY(2px)"})])])]),s("layout-toggle-button",`
 left: 0;
 transform: translateX(-50%) translateY(-50%);
 `,[s("base-icon",`
 transform: rotate(0);
 `)]),s("layout-toggle-bar",`
 left: -28px;
 transform: rotate(180deg);
 `,[b("&:hover",[u("top",{transform:"rotate(12deg) scale(1.15) translateY(-2px)"}),u("bottom",{transform:"rotate(-12deg) scale(1.15) translateY(2px)"})])])]),z("collapsed",[s("layout-toggle-bar",[b("&:hover",[u("top",{transform:"rotate(-12deg) scale(1.15) translateY(-2px)"}),u("bottom",{transform:"rotate(12deg) scale(1.15) translateY(2px)"})])]),s("layout-toggle-button",[s("base-icon",`
 transform: rotate(0);
 `)])]),s("layout-toggle-button",`
 transition:
 color .3s var(--n-bezier),
 right .3s var(--n-bezier),
 left .3s var(--n-bezier),
 border-color .3s var(--n-bezier),
 background-color .3s var(--n-bezier);
 cursor: pointer;
 width: 24px;
 height: 24px;
 position: absolute;
 top: 50%;
 right: 0;
 border-radius: 50%;
 display: flex;
 align-items: center;
 justify-content: center;
 font-size: 18px;
 color: var(--n-toggle-button-icon-color);
 border: var(--n-toggle-button-border);
 background-color: var(--n-toggle-button-color);
 box-shadow: 0 2px 4px 0px rgba(0, 0, 0, .06);
 transform: translateX(50%) translateY(-50%);
 z-index: 1;
 `,[s("base-icon",`
 transition: transform .3s var(--n-bezier);
 transform: rotate(180deg);
 `)]),s("layout-toggle-bar",`
 cursor: pointer;
 height: 72px;
 width: 32px;
 position: absolute;
 top: calc(50% - 36px);
 right: -28px;
 `,[u("top, bottom",`
 position: absolute;
 width: 4px;
 border-radius: 2px;
 height: 38px;
 left: 14px;
 transition: 
 background-color .3s var(--n-bezier),
 transform .3s var(--n-bezier);
 `),u("bottom",`
 position: absolute;
 top: 34px;
 `),b("&:hover",[u("top",{transform:"rotate(12deg) scale(1.15) translateY(-2px)"}),u("bottom",{transform:"rotate(-12deg) scale(1.15) translateY(2px)"})]),u("top, bottom",{backgroundColor:"var(--n-toggle-bar-color)"}),b("&:hover",[u("top, bottom",{backgroundColor:"var(--n-toggle-bar-color-hover)"})])]),u("border",`
 position: absolute;
 top: 0;
 right: 0;
 bottom: 0;
 width: 1px;
 transition: background-color .3s var(--n-bezier);
 `),s("layout-sider-scroll-container",`
 flex-grow: 1;
 flex-shrink: 0;
 box-sizing: border-box;
 height: 100%;
 opacity: 0;
 transition: opacity .3s var(--n-bezier);
 max-width: 100%;
 `),z("show-content",[s("layout-sider-scroll-container",{opacity:1})]),z("absolute-positioned",`
 position: absolute;
 left: 0;
 top: 0;
 bottom: 0;
 `)]),Co=N({name:"LayoutToggleButton",props:{clsPrefix:{type:String,required:!0},onClick:Function},render(){const{clsPrefix:e}=this;return c("div",{class:`${e}-layout-toggle-button`,onClick:this.onClick},c(ge,{clsPrefix:e},{default:()=>c(Ze,null)}))}}),yo=N({props:{clsPrefix:{type:String,required:!0},onClick:Function},render(){const{clsPrefix:e}=this;return c("div",{onClick:this.onClick,class:`${e}-layout-toggle-bar`},c("div",{class:`${e}-layout-toggle-bar__top`}),c("div",{class:`${e}-layout-toggle-bar__bottom`}))}});const zo={position:Ce,bordered:Boolean,collapsedWidth:{type:Number,default:48},width:{type:[Number,String],default:272},contentStyle:{type:[String,Object],default:""},collapseMode:{type:String,default:"transform"},collapsed:{type:Boolean,default:void 0},defaultCollapsed:Boolean,showCollapsedContent:{type:Boolean,default:!0},showTrigger:{type:[Boolean,String],default:!1},nativeScrollbar:{type:Boolean,default:!0},inverted:Boolean,scrollbarProps:Object,triggerStyle:[String,Object],collapsedTriggerStyle:[String,Object],"onUpdate:collapsed":[Function,Array],onUpdateCollapsed:[Function,Array],onAfterEnter:Function,onAfterLeave:Function,onExpand:[Function,Array],onCollapse:[Function,Array],onScroll:Function};var Io=N({name:"LayoutSider",props:Object.assign(Object.assign({},L.props),zo),setup(e){const t=O(to),o=j(null),n=j(null),a=g(()=>Q(d.value?e.collapsedWidth:e.width)),l=g(()=>e.collapseMode!=="transform"?{}:{minWidth:Q(e.width)}),m=g(()=>t?t.siderPlacement:"left"),v=j(e.defaultCollapsed),d=te(Y(e,"collapsed"),v);function x(P,C){if(e.nativeScrollbar){const{value:H}=o;H&&(C===void 0?H.scrollTo(P):H.scrollTo(P,C))}else{const{value:H}=n;H&&H.scrollTo(P,C)}}function R(){const{"onUpdate:collapsed":P,onUpdateCollapsed:C,onExpand:H,onCollapse:K}=e,{value:M}=d;C&&$(C,!M),P&&$(P,!M),v.value=!M,M?H&&$(H):K&&$(K)}D(ye,{collapsedRef:d,collapseModeRef:Y(e,"collapseMode")});const{mergedClsPrefixRef:f,inlineThemeDisabled:p}=X(e),I=L("Layout","-layout-sider",xo,xe,e,f);function _(P){var C,H;P.propertyName==="max-width"&&(d.value?(C=e.onAfterLeave)===null||C===void 0||C.call(e):(H=e.onAfterEnter)===null||H===void 0||H.call(e))}const A={scrollTo:x},k=g(()=>{const{common:{cubicBezierEaseInOut:P},self:C}=I.value,{siderToggleButtonColor:H,siderToggleButtonBorder:K,siderToggleBarColor:M,siderToggleBarColorHover:B}=C,h={"--n-bezier":P,"--n-toggle-button-color":H,"--n-toggle-button-border":K,"--n-toggle-bar-color":M,"--n-toggle-bar-color-hover":B};return e.inverted?(h["--n-color"]=C.siderColorInverted,h["--n-text-color"]=C.textColorInverted,h["--n-border-color"]=C.siderBorderColorInverted,h["--n-toggle-button-icon-color"]=C.siderToggleButtonIconColorInverted,h.__invertScrollbar=C.__invertScrollbar):(h["--n-color"]=C.siderColor,h["--n-text-color"]=C.textColor,h["--n-border-color"]=C.siderBorderColor,h["--n-toggle-button-icon-color"]=C.siderToggleButtonIconColor),h}),w=p?re("layout-sider",g(()=>e.inverted?"a":"b"),k,e):void 0;return Object.assign({scrollableElRef:o,scrollbarInstRef:n,mergedClsPrefix:f,mergedTheme:I,styleMaxWidth:a,mergedCollapsed:d,scrollContainerStyle:l,siderPlacement:m,handleTransitionend:_,handleTriggerClick:R,inlineThemeDisabled:p,cssVars:k,themeClass:w==null?void 0:w.themeClass,onRender:w==null?void 0:w.onRender},A)},render(){var e;const{mergedClsPrefix:t,mergedCollapsed:o,showTrigger:n}=this;return(e=this.onRender)===null||e===void 0||e.call(this),c("aside",{class:[`${t}-layout-sider`,this.themeClass,`${t}-layout-sider--${this.position}-positioned`,`${t}-layout-sider--${this.siderPlacement}-placement`,this.bordered&&`${t}-layout-sider--bordered`,o&&`${t}-layout-sider--collapsed`,(!o||this.showCollapsedContent)&&`${t}-layout-sider--show-content`],onTransitionend:this.handleTransitionend,style:[this.inlineThemeDisabled?void 0:this.cssVars,{maxWidth:this.styleMaxWidth,width:Q(this.width)}]},this.nativeScrollbar?c("div",{class:`${t}-layout-sider-scroll-container`,onScroll:this.onScroll,style:[this.scrollContainerStyle,{overflow:"auto"},this.contentStyle],ref:"scrollableElRef"},this.$slots):c(Qe,Object.assign({},this.scrollbarProps,{onScroll:this.onScroll,ref:"scrollbarInstRef",style:this.scrollContainerStyle,contentStyle:this.contentStyle,theme:this.mergedTheme.peers.Scrollbar,themeOverrides:this.mergedTheme.peerOverrides.Scrollbar,builtinThemeOverrides:this.inverted&&this.cssVars.__invertScrollbar==="true"?{colorHover:"rgba(255, 255, 255, .4)",color:"rgba(255, 255, 255, .3)"}:void 0}),this.$slots),n?n==="bar"?c(yo,{clsPrefix:t,style:o?this.collapsedTriggerStyle:this.triggerStyle,onClick:this.handleTriggerClick}):c(Co,{clsPrefix:t,style:o?this.collapsedTriggerStyle:this.triggerStyle,onClick:this.handleTriggerClick}):null,this.bordered?c("div",{class:`${t}-layout-sider__border`}):null)}});const q=ne("n-menu"),le=ne("n-submenu"),ae=ne("n-menu-item-group"),G=8;function ce(e){const t=O(q),{props:o,mergedCollapsedRef:n}=t,a=O(le,null),l=O(ae,null),m=g(()=>o.mode==="horizontal"),v=g(()=>m.value?o.dropdownPlacement:"tmNodes"in e?"right-start":"right"),d=g(()=>{var p;return Math.max((p=o.collapsedIconSize)!==null&&p!==void 0?p:o.iconSize,o.iconSize)}),x=g(()=>{var p;return!m.value&&e.root&&n.value&&(p=o.collapsedIconSize)!==null&&p!==void 0?p:o.iconSize}),R=g(()=>{if(m.value)return;const{collapsedWidth:p,indent:I,rootIndent:_}=o,{root:A,isGroup:k}=e,w=_===void 0?I:_;if(A)return n.value?p/2-d.value/2:w;if(l)return I/2+l.paddingLeftRef.value;if(a)return(k?I/2:I)+a.paddingLeftRef.value}),f=g(()=>{const{collapsedWidth:p,indent:I,rootIndent:_}=o,{value:A}=d,{root:k}=e;return m.value||!k||!n.value?G:(_===void 0?I:_)+A+G-(p+A)/2});return{dropdownPlacement:v,activeIconSize:x,maxIconSize:d,paddingLeft:R,iconMarginRight:f,NMenu:t,NSubmenu:a}}const de={internalKey:{type:[String,Number],required:!0},root:Boolean,isGroup:Boolean,level:{type:Number,required:!0},title:[String,Function],extra:[String,Function]},ze=Object.assign(Object.assign({},de),{tmNodes:{type:Array,required:!0}}),wo=N({name:"MenuOptionGroup",props:ze,setup(e){D(le,null);const t=ce(e);D(ae,{paddingLeftRef:t.paddingLeft});const{mergedClsPrefixRef:o,props:n}=O(q);return function(){const{value:a}=o,l=t.paddingLeft.value;return c("div",{class:`${a}-menu-item-group`,role:"group"},c("span",{class:`${a}-menu-item-group-title`,style:l!==void 0?`padding-left: ${l}px;`:void 0},V(e.title),e.extra?c(Ne,null," ",V(e.extra)):null),c("div",null,e.tmNodes.map(m=>se(m,n))))}}});var Ie=N({name:"MenuOptionContent",props:{collapsed:Boolean,disabled:Boolean,title:[String,Function],icon:Function,extra:[String,Function],showArrow:Boolean,childActive:Boolean,hover:Boolean,paddingLeft:Number,selected:Boolean,maxIconSize:{type:Number,required:!0},activeIconSize:{type:Number,required:!0},iconMarginRight:{type:Number,required:!0},clsPrefix:{type:String,required:!0},onClick:Function,tmNode:{type:Object,required:!0}},setup(e){const{props:t}=O(q);return{menuProps:t,style:g(()=>{const{paddingLeft:o}=e;return{paddingLeft:o&&`${o}px`}}),iconStyle:g(()=>{const{maxIconSize:o,activeIconSize:n,iconMarginRight:a}=e;return{width:`${o}px`,height:`${o}px`,fontSize:`${n}px`,marginRight:`${a}px`}})}},render(){const{clsPrefix:e,tmNode:t,menuProps:{renderIcon:o,renderLabel:n,renderExtra:a,expandIcon:l}}=this,m=o?o(t.rawNode):V(this.icon);return c("div",{onClick:this.onClick,role:"none",class:[`${e}-menu-item-content`,{[`${e}-menu-item-content--selected`]:this.selected,[`${e}-menu-item-content--collapsed`]:this.collapsed,[`${e}-menu-item-content--child-active`]:this.childActive,[`${e}-menu-item-content--disabled`]:this.disabled,[`${e}-menu-item-content--hover`]:this.hover}],style:this.style},m&&c("div",{class:`${e}-menu-item-content__icon`,style:this.iconStyle,role:"none"},[m]),c("div",{class:`${e}-menu-item-content-header`,role:"none"},n?n(t.rawNode):V(this.title),this.extra||a?c("span",{class:`${e}-menu-item-content-header__extra`}," ",a?a(t.rawNode):V(this.extra)):null),this.showArrow?c(ge,{ariaHidden:!0,class:`${e}-menu-item-content__arrow`,clsPrefix:e},{default:()=>l?l(t.rawNode):c(no,null)}):null)}});const we=Object.assign(Object.assign({},de),{rawNodes:{type:Array,default:()=>[]},tmNodes:{type:Array,default:()=>[]},tmNode:{type:Object,required:!0},disabled:{type:Boolean,default:!1},icon:Function,onClick:Function}),So=N({name:"Submenu",props:we,setup(e){const t=ce(e),{NMenu:o,NSubmenu:n}=t,{props:a,mergedCollapsedRef:l,mergedThemeRef:m}=o,v=g(()=>{const{disabled:p}=e;return n!=null&&n.mergedDisabledRef.value||a.disabled?!0:p}),d=j(!1);D(le,{paddingLeftRef:t.paddingLeft,mergedDisabledRef:v}),D(ae,null);function x(){const{onClick:p}=e;p&&p()}function R(){v.value||(l.value||o.toggleExpand(e.internalKey),x())}function f(p){d.value=p}return{menuProps:a,mergedTheme:m,doSelect:o.doSelect,inverted:o.invertedRef,isHorizontal:o.isHorizontalRef,mergedClsPrefix:o.mergedClsPrefixRef,maxIconSize:t.maxIconSize,activeIconSize:t.activeIconSize,iconMarginRight:t.iconMarginRight,dropdownPlacement:t.dropdownPlacement,dropdownShow:d,paddingLeft:t.paddingLeft,mergedDisabled:v,mergedValue:o.mergedValueRef,childActive:fe(()=>o.activePathRef.value.includes(e.internalKey)),collapsed:g(()=>a.mode==="horizontal"?!1:l.value?!0:!o.mergedExpandedKeysRef.value.includes(e.internalKey)),dropdownEnabled:g(()=>!v.value&&(a.mode==="horizontal"||l.value)),handlePopoverShowChange:f,handleClick:R}},render(){var e;const{mergedClsPrefix:t,menuProps:{renderIcon:o,renderLabel:n}}=this,a=()=>{const{isHorizontal:m,paddingLeft:v,collapsed:d,mergedDisabled:x,maxIconSize:R,activeIconSize:f,title:p,childActive:I,icon:_,handleClick:A,dropdownShow:k,iconMarginRight:w,tmNode:P}=this;return c(Ie,{tmNode:P,paddingLeft:v,collapsed:d,disabled:x,iconMarginRight:w,maxIconSize:R,activeIconSize:f,title:p,showArrow:!m,childActive:I,clsPrefix:t,icon:_,hover:k,onClick:A})},l=()=>c(ke,null,{default:()=>{const{tmNodes:m,collapsed:v}=this;return v?null:c("div",{class:`${t}-submenu-children`,role:"menu"},m.map(d=>se(d,this.menuProps)))}});return this.root?c(eo,Object.assign({},(e=this.menuProps)===null||e===void 0?void 0:e.dropdownProps,{themeOverrides:this.mergedTheme.peerOverrides.Dropdown,theme:this.mergedTheme.peers.Dropdown,builtinThemeOverrides:{fontSizeLarge:"14px",optionIconSizeLarge:"18px"},value:this.mergedValue,size:"large",trigger:"hover",disabled:!this.dropdownEnabled,placement:this.dropdownPlacement,keyField:this.menuProps.keyField,labelField:this.menuProps.labelField,childrenField:this.menuProps.childrenField,onUpdateShow:this.handlePopoverShowChange,options:this.rawNodes,onSelect:this.doSelect,inverted:this.inverted,renderIcon:o,renderLabel:n}),{default:()=>c("div",{class:`${t}-submenu`,role:"menuitem","aria-expanded":!this.collapsed},a(),this.isHorizontal?null:l())}):c("div",{class:`${t}-submenu`,role:"menuitem","aria-expanded":!this.collapsed},a(),l())}}),Se=Object.assign(Object.assign({},de),{tmNode:{type:Object,required:!0},disabled:Boolean,icon:Function,onClick:Function}),_o=N({name:"MenuOption",props:Se,setup(e){const t=ce(e),{NSubmenu:o,NMenu:n}=t,{props:a,mergedClsPrefixRef:l,mergedCollapsedRef:m}=n,v=o?o.mergedDisabledRef:{value:!1},d=g(()=>v.value||e.disabled);function x(f){const{onClick:p}=e;p&&p(f)}function R(f){d.value||(n.doSelect(e.internalKey,e.tmNode.rawNode),x(f))}return{mergedClsPrefix:l,dropdownPlacement:t.dropdownPlacement,paddingLeft:t.paddingLeft,iconMarginRight:t.iconMarginRight,maxIconSize:t.maxIconSize,activeIconSize:t.activeIconSize,mergedTheme:n.mergedThemeRef,menuProps:a,dropdownEnabled:fe(()=>e.root&&m.value&&a.mode!=="horizontal"&&!d.value),selected:g(()=>n.mergedValueRef.value===e.internalKey),mergedDisabled:d,handleClick:R}},render(){const{mergedClsPrefix:e,mergedTheme:t,tmNode:o,menuProps:{renderLabel:n}}=this;return c("div",{role:"menuitem",class:[`${e}-menu-item`]},c(qe,{theme:t.peers.Tooltip,themeOverrides:t.peerOverrides.Tooltip,trigger:"hover",placement:this.dropdownPlacement,disabled:!this.dropdownEnabled||this.title===void 0,internalExtraClass:["menu-tooltip"]},{default:()=>n?n(o.rawNode):V(this.title),trigger:()=>c(Ie,{tmNode:o,clsPrefix:e,paddingLeft:this.paddingLeft,iconMarginRight:this.iconMarginRight,maxIconSize:this.maxIconSize,activeIconSize:this.activeIconSize,selected:this.selected,title:this.title,extra:this.extra,disabled:this.mergedDisabled,icon:this.icon,onClick:this.handleClick})}))}});var Ao=N({name:"MenuDivider",setup(){const e=O(q),{mergedClsPrefixRef:t,isHorizontalRef:o}=e;return()=>o.value?null:c("div",{class:`${t.value}-menu-divider`})}});const Ho=ie(ze),Ro=ie(Se),Po=ie(we);function To(e){return e.type==="divider"||e.type==="render"}function No(e){return e.type==="divider"}function se(e,t){const{rawNode:o}=e;if(To(o))return No(o)?c(Ao,Object.assign({key:e.key},o.props)):void 0;const{labelField:n}=t,{key:a,level:l,isGroup:m}=e,v=Object.assign(Object.assign({},o),{title:o.title||o[n],extra:o.titleExtra||o.extra,key:a,internalKey:a,level:l,root:l===0,isGroup:m});return e.children?e.isGroup?c(wo,ee(v,Ho,{tmNodes:e.children,key:a})):c(So,ee(v,Po,{key:a,rawNodes:o[t.childrenField],tmNodes:e.children,tmNode:e})):c(_o,ee(v,Ro,{key:a,tmNode:e}))}var ko=b([s("menu",`
 background-color: var(--n-color);
 color: var(--n-item-text-color);
 overflow: hidden;
 transition: background-color .3s var(--n-bezier);
 box-sizing: border-box;
 font-size: var(--n-font-size);
 padding-bottom: 6px;
 `,[z("horizontal",`
 display: inline-flex;
 padding-bottom: 0;
 `,[s("submenu","margin: 0;"),s("menu-item","margin: 0;"),s("menu-item-content",`
 padding: 0 20px;
 border-bottom: 2px solid #0000;
 `,[b("&::before","display: none;"),z("selected","border-bottom: 2px solid var(--n-border-color-horizontal)")]),s("menu-item-content",[z("selected",[u("icon","color: var(--n-item-icon-color-active-horizontal);"),s("menu-item-content-header",`
 color: var(--n-item-text-color-active-horizontal);
 `,[b("a","color: var(--n-item-text-color-active-horizontal);"),u("extra","color: var(--n-item-text-color-active-horizontal);")])]),z("child-active",`
 border-bottom: 2px solid var(--n-border-color-horizontal);
 `,[s("menu-item-content-header",`
 color: var(--n-item-text-color-child-active-horizontal);
 `,[b("a",`
 color: var(--n-item-text-color-child-active-horizontal);
 `),u("extra",`
 color: var(--n-item-text-color-child-active-horizontal);
 `)]),u("icon",`
 color: var(--n-item-icon-color-child-active-horizontal);
 `)]),ue("disabled",[z("selected, child-active",[U(null,[u("icon","color: var(--n-item-icon-color-active-hover-horizontal);"),s("menu-item-content-header",`
 color: var(--n-item-text-color-active-hover-horizontal);
 `,[b("a","color: var(--n-item-text-color-active-hover-horizontal);"),u("extra","color: var(--n-item-text-color-active-hover-horizontal);")])])]),U("border-bottom: 2px solid var(--n-border-color-horizontal);",[u("icon",`
 color: var(--n-item-icon-color-hover-horizontal);
 `),s("menu-item-content-header",`
 color: var(--n-item-text-color-hover-horizontal);
 `,[b("a",`
 color: var(--n-item-text-color-hover-horizontal);
 `),u("extra",`
 color: var(--n-item-text-color-hover-horizontal);
 `)])])]),s("menu-item-content-header",[b("a","color: var(--n-item-text-color-horizontal);")])])]),z("collapsed",[s("menu-item",[z("selected",[b("&::before",`
 background-color: var(--n-item-color-active-collapsed) !important;
 `)])]),s("menu-item-content",[s("menu-item-content-header","opacity: 0;"),u("arrow","opacity: 0;"),u("icon","color: var(--n-item-icon-color-collapsed);")])]),s("menu-item",`
 height: var(--n-item-height);
 margin-top: 6px;
 position: relative;
 `),s("menu-item-content",`
 box-sizing: border-box;
 line-height: 1.75;
 height: 100%;
 display: grid;
 grid-template-areas: "icon content arrow";
 grid-template-columns: auto 1fr auto;
 align-items: center;
 cursor: pointer;
 position: relative;
 padding-right: 18px;
 transition:
 background-color .3s var(--n-bezier),
 padding-left .3s var(--n-bezier),
 border-color .3s var(--n-bezier);
 `,[b("> *","z-index: 1;"),b("&::before",`
 z-index: auto;
 content: "";
 background-color: #0000;
 position: absolute;
 left: 8px;
 right: 8px;
 top: 0;
 bottom: 0;
 pointer-events: none;
 border-radius: var(--n-border-radius);
 transition: background-color .3s var(--n-bezier);
 `),z("disabled",`
 opacity: .45;
 cursor: not-allowed;
 `),z("collapsed",[u("arrow","transform: rotate(0);")]),z("selected",[b("&::before","background-color: var(--n-item-color-active);"),u("arrow","color: var(--n-arrow-color-active);"),u("icon","color: var(--n-item-icon-color-active);"),s("menu-item-content-header",`
 color: var(--n-item-text-color-active);
 `,[b("a","color: var(--n-item-text-color-active);"),u("extra","color: var(--n-item-text-color-active);")])]),z("child-active",[s("menu-item-content-header",`
 color: var(--n-item-text-color-child-active);
 `,[b("a",`
 color: var(--n-item-text-color-child-active);
 `),u("extra",`
 color: var(--n-item-text-color-child-active);
 `)]),u("arrow",`
 color: var(--n-arrow-color-child-active);
 `),u("icon",`
 color: var(--n-item-icon-color-child-active);
 `)]),ue("disabled",[z("selected, child-active",[U(null,[u("arrow","color: var(--n-arrow-color-active-hover);"),u("icon","color: var(--n-item-icon-color-active-hover);"),s("menu-item-content-header",`
 color: var(--n-item-text-color-active-hover);
 `,[b("a","color: var(--n-item-text-color-active-hover);"),u("extra","color: var(--n-item-text-color-active-hover);")])])]),z("selected",[U(null,[b("&::before","background-color: var(--n-item-color-active-hover);")])]),U(null,[b("&::before","background-color: var(--n-item-color-hover);"),u("arrow",`
 color: var(--n-arrow-color-hover);
 `),u("icon",`
 color: var(--n-item-icon-color-hover);
 `),s("menu-item-content-header",`
 color: var(--n-item-text-color-hover);
 `,[b("a",`
 color: var(--n-item-text-color-hover);
 `),u("extra",`
 color: var(--n-item-text-color-hover);
 `)])])]),u("icon",`
 grid-area: icon;
 color: var(--n-item-icon-color);
 transition:
 color .3s var(--n-bezier),
 font-size .3s var(--n-bezier),
 margin-right .3s var(--n-bezier);
 box-sizing: content-box;
 display: inline-flex;
 align-items: center;
 justify-content: center;
 `),u("arrow",`
 grid-area: arrow;
 font-size: 16px;
 color: var(--n-arrow-color);
 transform: rotate(180deg);
 opacity: 1;
 transition:
 color .3s var(--n-bezier),
 transform 0.2s var(--n-bezier),
 opacity 0.2s var(--n-bezier);
 `),s("menu-item-content-header",`
 grid-area: content;
 transition:
 color .3s var(--n-bezier),
 opacity .3s var(--n-bezier);
 opacity: 1;
 white-space: nowrap;
 overflow: hidden;
 text-overflow: ellipsis;
 color: var(--n-item-text-color);
 `,[b("a",`
 text-decoration: none;
 transition: color .3s var(--n-bezier);
 color: var(--n-item-text-color);
 `,[b("&::before",`
 content: "";
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 `)]),u("extra",`
 font-size: .93em;
 color: var(--n-group-text-color);
 transition: color .3s var(--n-bezier);
 `)])]),s("submenu",`
 cursor: pointer;
 position: relative;
 margin-top: 6px;
 `,[s("menu-item-content",`
 height: var(--n-item-height);
 `),s("submenu-children",`
 overflow: hidden;
 padding: 0;
 `,[$e({duration:".2s"})])]),s("menu-item-group",[s("menu-item-group-title",`
 margin-top: 6px;
 color: var(--n-group-text-color);
 cursor: default;
 font-size: .93em;
 height: 36px;
 display: flex;
 align-items: center;
 transition:
 padding-left .3s var(--n-bezier),
 color .3s var(--n-bezier);
 `)])]),s("menu-tooltip",[b("a",`
 color: inherit;
 text-decoration: none;
 `)]),s("menu-divider",`
 transition: background-color .3s var(--n-bezier);
 background-color: var(--n-divider-color);
 height: 1px;
 margin: 6px 18px;
 `)]);function U(e,t){return[z("hover",e,t),b("&:hover",e,t)]}const $o=Object.assign(Object.assign({},L.props),{options:{type:Array,default:()=>[]},collapsed:{type:Boolean,default:void 0},collapsedWidth:{type:Number,default:48},iconSize:{type:Number,default:20},collapsedIconSize:{type:Number,default:24},rootIndent:Number,indent:{type:Number,default:32},labelField:{type:String,default:"label"},keyField:{type:String,default:"key"},childrenField:{type:String,default:"children"},defaultExpandAll:Boolean,defaultExpandedKeys:Array,expandedKeys:Array,value:[String,Number],defaultValue:{type:[String,Number],default:null},mode:{type:String,default:"vertical"},watchProps:{type:Array,default:void 0},disabled:Boolean,inverted:Boolean,"onUpdate:expandedKeys":[Function,Array],onUpdateExpandedKeys:[Function,Array],onUpdateValue:[Function,Array],"onUpdate:value":[Function,Array],expandIcon:Function,renderIcon:Function,renderLabel:Function,renderExtra:Function,dropdownPlacement:{type:String,default:"bottom"},dropdownProps:Object,accordion:Boolean,items:Array,onOpenNamesChange:[Function,Array],onSelect:[Function,Array],onExpandedNamesChange:[Function,Array],expandedNames:Array,defaultExpandedNames:Array});var Mo=N({name:"Menu",props:$o,setup(e){const{mergedClsPrefixRef:t,inlineThemeDisabled:o}=X(e),n=L("Menu","-menu",ko,po,e,t),a=O(ye,null),l=g(()=>{var h;const{collapsed:y}=e;if(y!==void 0)return y;if(a){const{collapseModeRef:r,collapsedRef:S}=a;if(r.value==="width")return(h=S.value)!==null&&h!==void 0?h:!1}return!1}),m=g(()=>{const{keyField:h,childrenField:y}=e;return oo(e.items||e.options,{getChildren(r){return r[y]},getKey(r){var S;return(S=r[h])!==null&&S!==void 0?S:r.name}})}),v=g(()=>new Set(m.value.treeNodes.map(h=>h.key))),{watchProps:d}=e,x=j(null);d!=null&&d.includes("defaultValue")?me(()=>{x.value=e.defaultValue}):x.value=e.defaultValue;const R=Y(e,"value"),f=te(R,x),p=j([]),I=()=>{p.value=e.defaultExpandAll?m.value.getNonLeafKeys():e.defaultExpandedNames||e.defaultExpandedKeys||m.value.getPath(f.value,{includeSelf:!1}).keyPath};d!=null&&d.includes("defaultExpandedKeys")?me(I):I();const _=Ge(e,["expandedNames","expandedKeys"]),A=te(_,p),k=g(()=>m.value.treeNodes),w=g(()=>m.value.getPath(f.value).keyPath);D(q,{props:e,mergedCollapsedRef:l,mergedThemeRef:n,mergedValueRef:f,mergedExpandedKeysRef:A,activePathRef:w,mergedClsPrefixRef:t,isHorizontalRef:g(()=>e.mode==="horizontal"),invertedRef:Y(e,"inverted"),doSelect:P,toggleExpand:H});function P(h,y){const{"onUpdate:value":r,onUpdateValue:S,onSelect:T}=e;S&&$(S,h,y),r&&$(r,h,y),T&&$(T,h,y),x.value=h}function C(h){const{"onUpdate:expandedKeys":y,onUpdateExpandedKeys:r,onExpandedNamesChange:S,onOpenNamesChange:T}=e;y&&$(y,h),r&&$(r,h),S&&$(S,h),T&&$(T,h),p.value=h}function H(h){const y=Array.from(A.value),r=y.findIndex(S=>S===h);if(~r)y.splice(r,1);else{if(e.accordion&&v.value.has(h)){const S=y.findIndex(T=>v.value.has(T));S>-1&&y.splice(S,1)}y.push(h)}C(y)}const K=h=>{const y=m.value.getPath(h!=null?h:f.value,{includeSelf:!1}).keyPath;if(!y.length)return;const r=Array.from(A.value),S=new Set([...r,...y]);e.accordion&&v.value.forEach(T=>{S.has(T)&&!y.includes(T)&&S.delete(T)}),C(Array.from(S))},M=g(()=>{const{inverted:h}=e,{common:{cubicBezierEaseInOut:y},self:r}=n.value,{borderRadius:S,borderColorHorizontal:T,fontSize:_e,itemHeight:Ae,dividerColor:He}=r,i={"--n-divider-color":He,"--n-bezier":y,"--n-font-size":_e,"--n-border-color-horizontal":T,"--n-border-radius":S,"--n-item-height":Ae};return h?(i["--n-group-text-color"]=r.groupTextColorInverted,i["--n-color"]=r.colorInverted,i["--n-item-text-color"]=r.itemTextColorInverted,i["--n-item-text-color-hover"]=r.itemTextColorHoverInverted,i["--n-item-text-color-active"]=r.itemTextColorActiveInverted,i["--n-item-text-color-child-active"]=r.itemTextColorChildActiveInverted,i["--n-item-text-color-active-hover"]=r.itemTextColorActiveHoverInverted,i["--n-item-icon-color"]=r.itemIconColorInverted,i["--n-item-icon-color-hover"]=r.itemIconColorHoverInverted,i["--n-item-icon-color-active"]=r.itemIconColorActiveInverted,i["--n-item-icon-color-active-hover"]=r.itemIconColorActiveHoverInverted,i["--n-item-icon-color-child-active"]=r.itemIconColorChildActiveInverted,i["--n-item-icon-color-collapsed"]=r.itemIconColorCollapsedInverted,i["--n-item-text-color-horizontal"]=r.itemTextColorHorizontalInverted,i["--n-item-text-color-hover-horizontal"]=r.itemTextColorHoverHorizontalInverted,i["--n-item-text-color-active-horizontal"]=r.itemTextColorActiveHorizontalInverted,i["--n-item-text-color-child-active-horizontal"]=r.itemTextColorChildActiveHorizontalInverted,i["--n-item-text-color-active-hover-horizontal"]=r.itemTextColorActiveHoverHorizontalInverted,i["--n-item-icon-color-horizontal"]=r.itemIconColorHorizontalInverted,i["--n-item-icon-color-hover-horizontal"]=r.itemIconColorHoverHorizontalInverted,i["--n-item-icon-color-active-horizontal"]=r.itemIconColorActiveHorizontalInverted,i["--n-item-icon-color-active-hover-horizontal"]=r.itemIconColorActiveHoverHorizontalInverted,i["--n-item-icon-color-child-active-horizontal"]=r.itemIconColorChildActiveHorizontalInverted,i["--n-arrow-color"]=r.arrowColorInverted,i["--n-arrow-color-hover"]=r.arrowColorHoverInverted,i["--n-arrow-color-active"]=r.arrowColorActiveInverted,i["--n-arrow-color-active-hover"]=r.arrowColorActiveHoverInverted,i["--n-arrow-color-child-active"]=r.arrowColorChildActiveInverted,i["--n-item-color-hover"]=r.itemColorHoverInverted,i["--n-item-color-active"]=r.itemColorActiveInverted,i["--n-item-color-active-hover"]=r.itemColorActiveHoverInverted,i["--n-item-color-active-collapsed"]=r.itemColorActiveCollapsedInverted):(i["--n-group-text-color"]=r.groupTextColor,i["--n-color"]=r.color,i["--n-item-text-color"]=r.itemTextColor,i["--n-item-text-color-hover"]=r.itemTextColorHover,i["--n-item-text-color-active"]=r.itemTextColorActive,i["--n-item-text-color-child-active"]=r.itemTextColorChildActive,i["--n-item-text-color-active-hover"]=r.itemTextColorActiveHover,i["--n-item-icon-color"]=r.itemIconColor,i["--n-item-icon-color-hover"]=r.itemIconColorHover,i["--n-item-icon-color-active"]=r.itemIconColorActive,i["--n-item-icon-color-active-hover"]=r.itemIconColorActiveHover,i["--n-item-icon-color-child-active"]=r.itemIconColorChildActive,i["--n-item-icon-color-collapsed"]=r.itemIconColorCollapsed,i["--n-item-text-color-horizontal"]=r.itemTextColorHorizontal,i["--n-item-text-color-hover-horizontal"]=r.itemTextColorHoverHorizontal,i["--n-item-text-color-active-horizontal"]=r.itemTextColorActiveHorizontal,i["--n-item-text-color-child-active-horizontal"]=r.itemTextColorChildActiveHorizontal,i["--n-item-text-color-active-hover-horizontal"]=r.itemTextColorActiveHoverHorizontal,i["--n-item-icon-color-horizontal"]=r.itemIconColorHorizontal,i["--n-item-icon-color-hover-horizontal"]=r.itemIconColorHoverHorizontal,i["--n-item-icon-color-active-horizontal"]=r.itemIconColorActiveHorizontal,i["--n-item-icon-color-active-hover-horizontal"]=r.itemIconColorActiveHoverHorizontal,i["--n-item-icon-color-child-active-horizontal"]=r.itemIconColorChildActiveHorizontal,i["--n-arrow-color"]=r.arrowColor,i["--n-arrow-color-hover"]=r.arrowColorHover,i["--n-arrow-color-active"]=r.arrowColorActive,i["--n-arrow-color-active-hover"]=r.arrowColorActiveHover,i["--n-arrow-color-child-active"]=r.arrowColorChildActive,i["--n-item-color-hover"]=r.itemColorHover,i["--n-item-color-active"]=r.itemColorActive,i["--n-item-color-active-hover"]=r.itemColorActiveHover,i["--n-item-color-active-collapsed"]=r.itemColorActiveCollapsed),i}),B=o?re("menu",g(()=>e.inverted?"a":"b"),M,e):void 0;return{mergedClsPrefix:t,controlledExpandedKeys:_,uncontrolledExpanededKeys:p,mergedExpandedKeys:A,uncontrolledValue:x,mergedValue:f,activePath:w,tmNodes:k,mergedTheme:n,mergedCollapsed:l,cssVars:o?void 0:M,themeClass:B==null?void 0:B.themeClass,onRender:B==null?void 0:B.onRender,showOption:K}},render(){const{mergedClsPrefix:e,mode:t,themeClass:o,onRender:n}=this;return n==null||n(),c("div",{role:t==="horizontal"?"menubar":"menu",class:[`${e}-menu`,o,`${e}-menu--${t}`,this.mergedCollapsed&&`${e}-menu--collapsed`],style:this.cssVars},this.tmNodes.map(a=>se(a,this.$props)))}});const Bo={},Eo={style:{"font-size":"12px"}},Oo=Be(" \xA92020 - 2022 Power by WWW.XIAOSM.CN "),Lo=W("br",null,null,-1),Fo=W("a",{target:"_blank",rel:"noopener",href:"https://beian.miit.gov.cn/"},[W("span",null,"\u6E58ICP\u590718023752\u53F7-1")],-1),jo=[Oo,Lo,Fo];function Ko(e,t){return oe(),Me("div",Eo,jo)}var Vo=be(Bo,[["render",Ko]]);const Do=e=>(je("data-v-495630c6"),e=e(),Ke(),e),Uo=Do(()=>W("div",{class:"logo"},[W("span",{style:{"font-size":"2rem"}},"YCloud")],-1)),Wo=N({setup(e){const t=Ve();window.$message=t,Ee(()=>{console.log()});const o=document.getElementById("app-c").clientHeight;console.log(o);let n=j("Home");const a=[{label:()=>c(Z,{to:{name:"Home"}},{default:()=>"Home"}),key:"Home"},{label:()=>c(Z,{to:{name:"Explorer",params:{path:["local"]}}},{default:()=>"Local-Store"}),key:"Explorer"},{label:()=>c(Z,{to:{name:"Explorer",params:{path:["cloud"]}}},{default:()=>"Local-Store"}),key:"2"},{label:"Settings",key:"Settings"}],l=function(m,v){n.value=m};return(m,v)=>{const d=Mo,x=Io,R=Oe("router-view"),f=ro,p=bo,I=uo;return oe(),ve(I,{vertical:"",size:"large"},{default:F(()=>[E(f,{style:Le({height:he(o)+"px"})},{default:F(()=>[E(f,{"has-sider":"",position:"absolute","content-style":"border-radius: 20px;"},{default:F(()=>[E(x,{"collapse-mode":"width","collapsed-width":100,width:200,"show-trigger":"arrow-circle","content-style":"padding: 24px;",bordered:""},{default:F(()=>[Uo,E(d,{value:he(n),"collapsed-width":64,"collapsed-icon-size":22,options:a,"onUpdate:value":l},null,8,["value"])]),_:1}),E(f,{"content-style":"padding: 20px;padding-bottom: 0;display: flex;flex-direction: column"},{default:F(()=>[E(f,{"content-style":"background-color: #fff;"},{default:F(()=>[E(R,{style:{height:"100%"}})]),_:1}),E(p,{bordered:"",style:{height:"60px",display:"flex","align-items":"center","justify-content":"center"}},{default:F(()=>[(oe(),ve(Fe(Vo)))]),_:1})]),_:1})]),_:1})]),_:1},8,["style"])]),_:1})}}});var et=be(Wo,[["__scopeId","data-v-495630c6"]]);export{et as default};
