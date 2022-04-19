import{n as fo,p as ho,c as L,a1 as po,o as vo,i as G,d as lo,aO as go,t as Wo,r as A,ax as ko,h as D,m as c,az as Mo,S as mo,f as Oo,w as so,j as C,l as u,u as Co,aN as No,a as yo,D as jo,s as Lo,b as t,g as Z,k as _o,v as Ko,aM as Vo,aP as Qo}from"./index.b56e0f28.js";import{u as qo,r as uo,i as Ao,c as Yo}from"./use-rtl.e209f69a.js";function bo(o){return o.replace(/#|\(|\)|,|\s/g,"_")}const xo=vo("n-form-item");function Uo(o,{defaultSize:f="medium",mergedSize:y,mergedDisabled:h}={}){const n=fo(xo,null);ho(xo,null);const R=L(y?()=>y(n):()=>{const{size:T}=o;if(T)return T;if(n){const{mergedSize:K}=n;if(K.value!==void 0)return K.value}return f}),O=L(h?()=>h(n):()=>{const{disabled:T}=o;return T!==void 0?T:n?n.disabled.value:!1}),_=L(()=>{const{status:T}=o;return T||(n==null?void 0:n.mergedValidationStatus.value)});return po(()=>{n&&n.restoreValidation()}),{mergedSizeRef:R,mergedDisabledRef:O,mergedStatusRef:_,nTriggerFormBlur(){n&&n.handleContentBlur()},nTriggerFormChange(){n&&n.handleContentChange()},nTriggerFormFocus(){n&&n.handleContentFocus()},nTriggerFormInput(){n&&n.handleContentInput()}}}var Jo=G("base-wave",`
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 border-radius: inherit;
`),Xo=lo({name:"BaseWave",props:{clsPrefix:{type:String,required:!0}},setup(o){go("-base-wave",Jo,Wo(o,"clsPrefix"));const f=A(null),y=A(!1);let h=null;return po(()=>{h!==null&&window.clearTimeout(h)}),{active:y,selfRef:f,play(){h!==null&&(window.clearTimeout(h),y.value=!1,h=null),ko(()=>{var n;(n=f.value)===null||n===void 0||n.offsetHeight,y.value=!0,h=window.setTimeout(()=>{y.value=!1,h=null},1e3)})}}},render(){const{clsPrefix:o}=this;return D("div",{ref:"selfRef","aria-hidden":!0,class:[`${o}-base-wave`,this.active&&`${o}-base-wave--active`]})}});const{cubicBezierEaseInOut:M}=Mo;function Zo({duration:o=".2s",delay:f=".1s"}={}){return[c("&.fade-in-width-expand-transition-leave-from, &.fade-in-width-expand-transition-enter-to",{opacity:1}),c("&.fade-in-width-expand-transition-leave-to, &.fade-in-width-expand-transition-enter-from",`
 opacity: 0!important;
 margin-left: 0!important;
 margin-right: 0!important;
 `),c("&.fade-in-width-expand-transition-leave-active",`
 overflow: hidden;
 transition:
 opacity ${o} ${M},
 max-width ${o} ${M} ${f},
 margin-left ${o} ${M} ${f},
 margin-right ${o} ${M} ${f};
 `),c("&.fade-in-width-expand-transition-enter-active",`
 overflow: hidden;
 transition:
 opacity ${o} ${M} ${f},
 max-width ${o} ${M},
 margin-left ${o} ${M},
 margin-right ${o} ${M};
 `)]}function j(o){return mo(o,[255,255,255,.16])}function oo(o){return mo(o,[0,0,0,.12])}var oe={paddingTiny:"0 6px",paddingSmall:"0 10px",paddingMedium:"0 14px",paddingLarge:"0 18px",paddingRoundTiny:"0 10px",paddingRoundSmall:"0 14px",paddingRoundMedium:"0 18px",paddingRoundLarge:"0 22px",iconMarginTiny:"6px",iconMarginSmall:"6px",iconMarginMedium:"6px",iconMarginLarge:"6px",iconSizeTiny:"14px",iconSizeSmall:"18px",iconSizeMedium:"18px",iconSizeLarge:"20px",rippleDuration:".6s"};const ee=o=>{const{heightTiny:f,heightSmall:y,heightMedium:h,heightLarge:n,borderRadius:R,fontSizeTiny:O,fontSizeSmall:_,fontSizeMedium:T,fontSizeLarge:K,opacityDisabled:eo,textColor2:$,textColor3:ro,primaryColorHover:p,primaryColorPressed:w,borderColor:Y,primaryColor:E,baseColor:s,infoColor:I,infoColorHover:P,infoColorPressed:r,successColor:l,successColorHover:g,successColorPressed:e,warningColor:z,warningColorHover:S,warningColorPressed:W,errorColor:H,errorColorHover:v,errorColorPressed:k,fontWeight:B,buttonColor2:V,buttonColor2Hover:F,buttonColor2Pressed:a,fontWeightStrong:U}=o;return Object.assign(Object.assign({},oe),{heightTiny:f,heightSmall:y,heightMedium:h,heightLarge:n,borderRadiusTiny:R,borderRadiusSmall:R,borderRadiusMedium:R,borderRadiusLarge:R,fontSizeTiny:O,fontSizeSmall:_,fontSizeMedium:T,fontSizeLarge:K,opacityDisabled:eo,colorOpacitySecondary:"0.16",colorOpacitySecondaryHover:"0.22",colorOpacitySecondaryPressed:"0.28",colorSecondary:V,colorSecondaryHover:F,colorSecondaryPressed:a,colorTertiary:V,colorTertiaryHover:F,colorTertiaryPressed:a,colorQuaternary:"#0000",colorQuaternaryHover:F,colorQuaternaryPressed:a,color:"#0000",colorHover:"#0000",colorPressed:"#0000",colorFocus:"#0000",colorDisabled:"#0000",textColor:$,textColorTertiary:ro,textColorHover:p,textColorPressed:w,textColorFocus:p,textColorDisabled:$,textColorText:$,textColorTextHover:p,textColorTextPressed:w,textColorTextFocus:p,textColorTextDisabled:$,textColorGhost:$,textColorGhostHover:p,textColorGhostPressed:w,textColorGhostFocus:p,textColorGhostDisabled:$,border:`1px solid ${Y}`,borderHover:`1px solid ${p}`,borderPressed:`1px solid ${w}`,borderFocus:`1px solid ${p}`,borderDisabled:`1px solid ${Y}`,rippleColor:E,colorPrimary:E,colorHoverPrimary:p,colorPressedPrimary:w,colorFocusPrimary:p,colorDisabledPrimary:E,textColorPrimary:s,textColorHoverPrimary:s,textColorPressedPrimary:s,textColorFocusPrimary:s,textColorDisabledPrimary:s,textColorTextPrimary:E,textColorTextHoverPrimary:p,textColorTextPressedPrimary:w,textColorTextFocusPrimary:p,textColorTextDisabledPrimary:$,textColorGhostPrimary:E,textColorGhostHoverPrimary:p,textColorGhostPressedPrimary:w,textColorGhostFocusPrimary:p,textColorGhostDisabledPrimary:E,borderPrimary:`1px solid ${E}`,borderHoverPrimary:`1px solid ${p}`,borderPressedPrimary:`1px solid ${w}`,borderFocusPrimary:`1px solid ${p}`,borderDisabledPrimary:`1px solid ${E}`,rippleColorPrimary:E,colorInfo:I,colorHoverInfo:P,colorPressedInfo:r,colorFocusInfo:P,colorDisabledInfo:I,textColorInfo:s,textColorHoverInfo:s,textColorPressedInfo:s,textColorFocusInfo:s,textColorDisabledInfo:s,textColorTextInfo:I,textColorTextHoverInfo:P,textColorTextPressedInfo:r,textColorTextFocusInfo:P,textColorTextDisabledInfo:$,textColorGhostInfo:I,textColorGhostHoverInfo:P,textColorGhostPressedInfo:r,textColorGhostFocusInfo:P,textColorGhostDisabledInfo:I,borderInfo:`1px solid ${I}`,borderHoverInfo:`1px solid ${P}`,borderPressedInfo:`1px solid ${r}`,borderFocusInfo:`1px solid ${P}`,borderDisabledInfo:`1px solid ${I}`,rippleColorInfo:I,colorSuccess:l,colorHoverSuccess:g,colorPressedSuccess:e,colorFocusSuccess:g,colorDisabledSuccess:l,textColorSuccess:s,textColorHoverSuccess:s,textColorPressedSuccess:s,textColorFocusSuccess:s,textColorDisabledSuccess:s,textColorTextSuccess:l,textColorTextHoverSuccess:g,textColorTextPressedSuccess:e,textColorTextFocusSuccess:g,textColorTextDisabledSuccess:$,textColorGhostSuccess:l,textColorGhostHoverSuccess:g,textColorGhostPressedSuccess:e,textColorGhostFocusSuccess:g,textColorGhostDisabledSuccess:l,borderSuccess:`1px solid ${l}`,borderHoverSuccess:`1px solid ${g}`,borderPressedSuccess:`1px solid ${e}`,borderFocusSuccess:`1px solid ${g}`,borderDisabledSuccess:`1px solid ${l}`,rippleColorSuccess:l,colorWarning:z,colorHoverWarning:S,colorPressedWarning:W,colorFocusWarning:S,colorDisabledWarning:z,textColorWarning:s,textColorHoverWarning:s,textColorPressedWarning:s,textColorFocusWarning:s,textColorDisabledWarning:s,textColorTextWarning:z,textColorTextHoverWarning:S,textColorTextPressedWarning:W,textColorTextFocusWarning:S,textColorTextDisabledWarning:$,textColorGhostWarning:z,textColorGhostHoverWarning:S,textColorGhostPressedWarning:W,textColorGhostFocusWarning:S,textColorGhostDisabledWarning:z,borderWarning:`1px solid ${z}`,borderHoverWarning:`1px solid ${S}`,borderPressedWarning:`1px solid ${W}`,borderFocusWarning:`1px solid ${S}`,borderDisabledWarning:`1px solid ${z}`,rippleColorWarning:z,colorError:H,colorHoverError:v,colorPressedError:k,colorFocusError:v,colorDisabledError:H,textColorError:s,textColorHoverError:s,textColorPressedError:s,textColorFocusError:s,textColorDisabledError:s,textColorTextError:H,textColorTextHoverError:v,textColorTextPressedError:k,textColorTextFocusError:v,textColorTextDisabledError:$,textColorGhostError:H,textColorGhostHoverError:v,textColorGhostPressedError:k,textColorGhostFocusError:v,textColorGhostDisabledError:H,borderError:`1px solid ${H}`,borderHoverError:`1px solid ${v}`,borderPressedError:`1px solid ${k}`,borderFocusError:`1px solid ${v}`,borderDisabledError:`1px solid ${H}`,rippleColorError:H,waveOpacity:"0.6",fontWeight:B,fontWeightStrong:U})},re={name:"Button",common:Oo,self:ee};var te=re;const d="0!important",Po="-1px!important";function Q(o){return C(o+"-type",[c("& +",[G("button",{},[C(o+"-type",[u("border",{borderLeftWidth:d}),u("state-border",{left:Po})])])])])}function q(o){return C(o+"-type",[c("& +",[G("button",[C(o+"-type",[u("border",{borderTopWidth:d}),u("state-border",{top:Po})])])])])}var ne=G("button-group",`
 flex-wrap: nowrap;
 display: inline-flex;
 position: relative;
`,[so("vertical",{flexDirection:"row"},[G("button",[c("&:first-child:not(:last-child)",`
 margin-right: ${d};
 border-top-right-radius: ${d};
 border-bottom-right-radius: ${d};
 `),c("&:last-child:not(:first-child)",`
 margin-left: ${d};
 border-top-left-radius: ${d};
 border-bottom-left-radius: ${d};
 `),c("&:not(:first-child):not(:last-child)",`
 margin-left: ${d};
 margin-right: ${d};
 border-radius: ${d};
 `),Q("default"),C("ghost",[Q("primary"),Q("info"),Q("success"),Q("warning"),Q("error")])])]),C("vertical",{flexDirection:"column"},[G("button",[c("&:first-child:not(:last-child)",`
 margin-bottom: ${d};
 margin-left: ${d};
 margin-right: ${d};
 border-bottom-left-radius: ${d};
 border-bottom-right-radius: ${d};
 `),c("&:last-child:not(:first-child)",`
 margin-top: ${d};
 margin-left: ${d};
 margin-right: ${d};
 border-top-left-radius: ${d};
 border-top-right-radius: ${d};
 `),c("&:not(:first-child):not(:last-child)",`
 margin: ${d};
 border-radius: ${d};
 `),q("default"),C("ghost",[q("primary"),q("info"),q("success"),q("warning"),q("error")])])])]);const $o=vo("n-button-group"),ie={size:{type:String,default:void 0},vertical:Boolean};lo({name:"ButtonGroup",props:ie,setup(o){const{mergedClsPrefixRef:f}=Co(o);return go("-button-group",ne,f),ho($o,o),{mergedClsPrefix:f}},render(){const{mergedClsPrefix:o}=this;return D("div",{class:[`${o}-button-group`,this.vertical&&`${o}-button-group--vertical`],role:"group"},this.$slots)}});var se=c([G("button",`
 margin: 0;
 font-weight: var(--n-font-weight);
 line-height: 1;
 font-family: inherit;
 padding: var(--n-padding);
 height: var(--n-height);
 font-size: var(--n-font-size);
 border-radius: var(--n-border-radius);
 color: var(--n-text-color);
 background-color: var(--n-color);
 width: var(--n-width);
 white-space: nowrap;
 outline: none;
 position: relative;
 z-index: auto;
 border: none;
 display: inline-flex;
 flex-wrap: nowrap;
 flex-shrink: 0;
 align-items: center;
 justify-content: center;
 user-select: none;
 text-align: center;
 cursor: pointer;
 text-decoration: none;
 transition:
 color .3s var(--n-bezier),
 background-color .3s var(--n-bezier),
 opacity .3s var(--n-bezier),
 border-color .3s var(--n-bezier);
 `,[C("color",[u("border",{borderColor:"var(--n-border-color)"}),C("disabled",[u("border",{borderColor:"var(--n-border-color-disabled)"})]),so("disabled",[c("&:focus",[u("state-border",{borderColor:"var(--n-border-color-focus)"})]),c("&:hover",[u("state-border",{borderColor:"var(--n-border-color-hover)"})]),c("&:active",[u("state-border",{borderColor:"var(--n-border-color-pressed)"})]),C("pressed",[u("state-border",{borderColor:"var(--n-border-color-pressed)"})])])]),C("disabled",{backgroundColor:"var(--n-color-disabled)",color:"var(--n-text-color-disabled)"},[u("border",{border:"var(--n-border-disabled)"})]),so("disabled",[c("&:focus",{backgroundColor:"var(--n-color-focus)",color:"var(--n-text-color-focus)"},[u("state-border",{border:"var(--n-border-focus)"})]),c("&:hover",{backgroundColor:"var(--n-color-hover)",color:"var(--n-text-color-hover)"},[u("state-border",{border:"var(--n-border-hover)"})]),c("&:active",{backgroundColor:"var(--n-color-pressed)",color:"var(--n-text-color-pressed)"},[u("state-border",{border:"var(--n-border-pressed)"})]),C("pressed",{backgroundColor:"var(--n-color-pressed)",color:"var(--n-text-color-pressed)"},[u("state-border",{border:"var(--n-border-pressed)"})])]),C("loading",{"pointer-events":"none"}),G("base-wave",`
 pointer-events: none;
 top: 0;
 right: 0;
 bottom: 0;
 left: 0;
 animation-iteration-count: 1;
 animation-duration: var(--n-ripple-duration);
 animation-timing-function: var(--n-bezier-ease-out), var(--n-bezier-ease-out);
 `,[C("active",{zIndex:1,animationName:"button-wave-spread, button-wave-opacity"})]),typeof window!="undefined"&&"MozBoxSizing"in document.createElement("div").style?c("&::moz-focus-inner",{border:0}):null,u("border, state-border",`
 position: absolute;
 left: 0;
 top: 0;
 right: 0;
 bottom: 0;
 border-radius: inherit;
 transition: border-color .3s var(--n-bezier);
 pointer-events: none;
 `),u("border",{border:"var(--n-border)"}),u("state-border",{border:"var(--n-border)",borderColor:"#0000",zIndex:1}),u("icon",`
 margin: var(--n-icon-margin);
 margin-left: 0;
 height: var(--n-icon-size);
 width: var(--n-icon-size);
 max-width: var(--n-icon-size);
 font-size: var(--n-icon-size);
 position: relative;
 flex-shrink: 0;
 `,[G("icon-slot",`
 height: var(--n-icon-size);
 width: var(--n-icon-size);
 position: absolute;
 left: 0;
 top: 50%;
 transform: translateY(-50%);
 display: flex;
 `,[No({top:"50%",originalTransform:"translateY(-50%)"})]),Zo()]),u("content",`
 display: flex;
 align-items: center;
 flex-wrap: nowrap;
 `,[c("~",[u("icon",{margin:"var(--n-icon-margin)",marginRight:0})])]),C("block",`
 display: flex;
 width: 100%;
 `),C("dashed",[u("border, state-border",{borderStyle:"dashed !important"})]),C("disabled",{cursor:"not-allowed",opacity:"var(--n-opacity-disabled)"})]),c("@keyframes button-wave-spread",{from:{boxShadow:"0 0 0.5px 0 var(--n-ripple-color)"},to:{boxShadow:"0 0 0.5px 4.5px var(--n-ripple-color)"}}),c("@keyframes button-wave-opacity",{from:{opacity:"var(--n-wave-opacity)"},to:{opacity:0}})]);const le=Object.assign(Object.assign({},yo.props),{color:String,textColor:String,text:Boolean,block:Boolean,loading:Boolean,disabled:Boolean,circle:Boolean,size:String,ghost:Boolean,round:Boolean,secondary:Boolean,tertiary:Boolean,quaternary:Boolean,strong:Boolean,focusable:{type:Boolean,default:!0},keyboard:{type:Boolean,default:!0},tag:{type:String,default:"button"},type:{type:String,default:"default"},dashed:Boolean,iconPlacement:{type:String,default:"left"},attrType:{type:String,default:"button"},bordered:{type:Boolean,default:!0},onClick:[Function,Array],internalAutoFocus:Boolean}),ae=lo({name:"Button",props:le,setup(o){const f=A(null),y=A(null),h=A(!1);jo(()=>{const{value:r}=f;r&&!o.disabled&&o.focusable&&o.internalAutoFocus&&r.focus({preventScroll:!0})});const n=Lo(()=>!o.quaternary&&!o.tertiary&&!o.secondary&&!o.text&&(!o.color||o.ghost||o.dashed)&&o.bordered),R=fo($o,{}),{mergedSizeRef:O}=Uo({},{defaultSize:"medium",mergedSize:r=>{const{size:l}=o;if(l)return l;const{size:g}=R;if(g)return g;const{mergedSize:e}=r||{};return e?e.value:"medium"}}),_=L(()=>o.focusable&&!o.disabled),T=r=>{var l;r.preventDefault(),!o.disabled&&_.value&&((l=f.value)===null||l===void 0||l.focus({preventScroll:!0}))},K=r=>{var l;if(!o.disabled&&!o.loading){const{onClick:g}=o;g&&Yo(g,r),o.text||(l=y.value)===null||l===void 0||l.play()}},eo=r=>{switch(r.code){case"Enter":case"NumpadEnter":if(!o.keyboard)return;h.value=!1}},$=r=>{switch(r.code){case"Enter":case"NumpadEnter":if(!o.keyboard||o.loading){r.preventDefault();return}h.value=!0}},ro=()=>{h.value=!1},{inlineThemeDisabled:p,mergedClsPrefixRef:w,mergedRtlRef:Y}=Co(o),E=yo("Button","-button",se,te,o,w),s=qo("Button",Y,w),I=L(()=>{const r=E.value,{common:{cubicBezierEaseInOut:l,cubicBezierEaseOut:g},self:e}=r,{rippleDuration:z,opacityDisabled:S,fontWeight:W,fontWeightStrong:H}=e,v=O.value,{dashed:k,type:B,ghost:V,text:F,color:a,round:U,circle:to,textColor:N,secondary:So,tertiary:ao,quaternary:To,strong:wo}=o,zo={"font-weight":wo?H:W};let b={"--n-color":"initial","--n-color-hover":"initial","--n-color-pressed":"initial","--n-color-focus":"initial","--n-color-disabled":"initial","--n-ripple-color":"initial","--n-text-color":"initial","--n-text-color-hover":"initial","--n-text-color-pressed":"initial","--n-text-color-focus":"initial","--n-text-color-disabled":"initial"};const J=B==="tertiary",co=B==="default",i=J?"default":B;if(F){const x=N||a,m=x||e[t("textColorText",i)];b={"--n-color":"#0000","--n-color-hover":"#0000","--n-color-pressed":"#0000","--n-color-focus":"#0000","--n-color-disabled":"#0000","--n-ripple-color":"#0000","--n-text-color":m,"--n-text-color-hover":x?j(x):e[t("textColorTextHover",i)],"--n-text-color-pressed":x?oo(x):e[t("textColorTextPressed",i)],"--n-text-color-focus":x?j(x):e[t("textColorTextHover",i)],"--n-text-color-disabled":x||e[t("textColorTextDisabled",i)]}}else if(V||k){const x=N||a;b={"--n-color":"#0000","--n-color-hover":"#0000","--n-color-pressed":"#0000","--n-color-focus":"#0000","--n-color-disabled":"#0000","--n-ripple-color":a||e[t("rippleColor",i)],"--n-text-color":x||e[t("textColorGhost",i)],"--n-text-color-hover":x?j(x):e[t("textColorGhostHover",i)],"--n-text-color-pressed":x?oo(x):e[t("textColorGhostPressed",i)],"--n-text-color-focus":x?j(x):e[t("textColorGhostHover",i)],"--n-text-color-disabled":x||e[t("textColorGhostDisabled",i)]}}else if(So){const x=co?e.textColor:J?e.textColorTertiary:e[t("color",i)],m=a||x,X=B!=="default"&&B!=="tertiary";b={"--n-color":X?Z(m,{alpha:Number(e.colorOpacitySecondary)}):e.colorSecondary,"--n-color-hover":X?Z(m,{alpha:Number(e.colorOpacitySecondaryHover)}):e.colorSecondaryHover,"--n-color-pressed":X?Z(m,{alpha:Number(e.colorOpacitySecondaryPressed)}):e.colorSecondaryPressed,"--n-color-focus":X?Z(m,{alpha:Number(e.colorOpacitySecondaryHover)}):e.colorSecondaryHover,"--n-color-disabled":e.colorSecondary,"--n-ripple-color":"#0000","--n-text-color":m,"--n-text-color-hover":m,"--n-text-color-pressed":m,"--n-text-color-focus":m,"--n-text-color-disabled":m}}else if(ao||To){const x=co?e.textColor:J?e.textColorTertiary:e[t("color",i)],m=a||x;ao?(b["--n-color"]=e.colorTertiary,b["--n-color-hover"]=e.colorTertiaryHover,b["--n-color-pressed"]=e.colorTertiaryPressed,b["--n-color-focus"]=e.colorSecondaryHover,b["--n-color-disabled"]=e.colorTertiary):(b["--n-color"]=e.colorQuaternary,b["--n-color-hover"]=e.colorQuaternaryHover,b["--n-color-pressed"]=e.colorQuaternaryPressed,b["--n-color-focus"]=e.colorQuaternaryHover,b["--n-color-disabled"]=e.colorQuaternary),b["--n-ripple-color"]="#0000",b["--n-text-color"]=m,b["--n-text-color-hover"]=m,b["--n-text-color-pressed"]=m,b["--n-text-color-focus"]=m,b["--n-text-color-disabled"]=m}else b={"--n-color":a||e[t("color",i)],"--n-color-hover":a?j(a):e[t("colorHover",i)],"--n-color-pressed":a?oo(a):e[t("colorPressed",i)],"--n-color-focus":a?j(a):e[t("colorFocus",i)],"--n-color-disabled":a||e[t("colorDisabled",i)],"--n-ripple-color":a||e[t("rippleColor",i)],"--n-text-color":N||(a?e.textColorPrimary:J?e.textColorTertiary:e[t("textColor",i)]),"--n-text-color-hover":N||(a?e.textColorHoverPrimary:e[t("textColorHover",i)]),"--n-text-color-pressed":N||(a?e.textColorPressedPrimary:e[t("textColorPressed",i)]),"--n-text-color-focus":N||(a?e.textColorFocusPrimary:e[t("textColorFocus",i)]),"--n-text-color-disabled":N||(a?e.textColorDisabledPrimary:e[t("textColorDisabled",i)])};let no={"--n-border":"initial","--n-border-hover":"initial","--n-border-pressed":"initial","--n-border-focus":"initial","--n-border-disabled":"initial"};F?no={"--n-border":"none","--n-border-hover":"none","--n-border-pressed":"none","--n-border-focus":"none","--n-border-disabled":"none"}:no={"--n-border":e[t("border",i)],"--n-border-hover":e[t("borderHover",i)],"--n-border-pressed":e[t("borderPressed",i)],"--n-border-focus":e[t("borderFocus",i)],"--n-border-disabled":e[t("borderDisabled",i)]};const{[t("height",v)]:io,[t("fontSize",v)]:Ho,[t("padding",v)]:Fo,[t("paddingRound",v)]:Do,[t("iconSize",v)]:Eo,[t("borderRadius",v)]:Io,[t("iconMargin",v)]:Bo,waveOpacity:Go}=e,Ro={"--n-width":to&&!F?io:"initial","--n-height":F?"initial":io,"--n-font-size":Ho,"--n-padding":to||F?"initial":U?Do:Fo,"--n-icon-size":Eo,"--n-icon-margin":Bo,"--n-border-radius":F?"initial":to||U?io:Io};return Object.assign(Object.assign(Object.assign(Object.assign({"--n-bezier":l,"--n-bezier-ease-out":g,"--n-ripple-duration":z,"--n-opacity-disabled":S,"--n-wave-opacity":Go},zo),b),no),Ro)}),P=p?_o("button",L(()=>{let r="";const{dashed:l,type:g,ghost:e,text:z,color:S,round:W,circle:H,textColor:v,secondary:k,tertiary:B,quaternary:V,strong:F}=o;l&&(r+="a"),e&&(r+="b"),z&&(r+="c"),W&&(r+="d"),H&&(r+="e"),k&&(r+="f"),B&&(r+="g"),V&&(r+="h"),F&&(r+="i"),S&&(r+="j"+bo(S)),v&&(r+="k"+bo(v));const{value:a}=O;return r+="l"+a[0],r+="m"+g[0],r}),I,o):void 0;return{selfElRef:f,waveElRef:y,mergedClsPrefix:w,mergedFocusable:_,mergedSize:O,showBorder:n,enterPressed:h,rtlEnabled:s,handleMousedown:T,handleKeydown:$,handleBlur:ro,handleKeyup:eo,handleClick:K,customColorCssVars:L(()=>{const{color:r}=o;if(!r)return null;const l=j(r);return{"--n-border-color":r,"--n-border-color-hover":l,"--n-border-color-pressed":oo(r),"--n-border-color-focus":l,"--n-border-color-disabled":r}}),cssVars:p?void 0:I,themeClass:P==null?void 0:P.themeClass,onRender:P==null?void 0:P.onRender}},render(){const{mergedClsPrefix:o,tag:f,onRender:y}=this;y==null||y();const h=uo(this.$slots.default,n=>n&&D("span",{class:`${o}-button__content`},n));return D(f,{ref:"selfElRef",class:[this.themeClass,`${o}-button`,`${o}-button--${this.type}-type`,`${o}-button--${this.mergedSize}-type`,this.rtlEnabled&&`${o}-button--rtl`,this.disabled&&`${o}-button--disabled`,this.block&&`${o}-button--block`,this.enterPressed&&`${o}-button--pressed`,!this.text&&this.dashed&&`${o}-button--dashed`,this.color&&`${o}-button--color`,this.secondary&&`${o}-button--secondary`,this.loading&&`${o}-button--loading`,this.ghost&&`${o}-button--ghost`],tabindex:this.mergedFocusable?0:-1,type:this.attrType,style:this.cssVars,disabled:this.disabled,onClick:this.handleClick,onBlur:this.handleBlur,onMousedown:this.handleMousedown,onKeyup:this.handleKeyup,onKeydown:this.handleKeydown},this.iconPlacement==="right"&&h,D(Ko,{width:!0},{default:()=>uo(this.$slots.icon,n=>(this.loading||n)&&D("span",{class:`${o}-button__icon`,style:{margin:Ao(this.$slots.default)?"0":""}},D(Vo,null,{default:()=>this.loading?D(Qo,{clsPrefix:o,key:"loading",class:`${o}-icon-slot`,strokeWidth:20}):D("div",{key:"icon",class:`${o}-icon-slot`,role:"none"},n)})))}),this.iconPlacement==="left"&&h,this.text?null:D(Xo,{ref:"waveElRef",clsPrefix:o}),this.showBorder?D("div",{"aria-hidden":!0,class:`${o}-button__border`,style:this.customColorCssVars}):null,this.showBorder?D("div",{"aria-hidden":!0,class:`${o}-button__state-border`,style:this.customColorCssVars}):null)}});var ue=ae;export{ue as N,Xo as a,te as b,bo as c,Zo as f,Uo as u};
