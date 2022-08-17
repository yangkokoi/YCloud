import{_ as R}from"./plugin-vue_export-helper.21dcd24c.js";import{b as D,i as F}from"./Icon.b642b959.js";import{_ as V,a as k}from"./Upload.85fd28f9.js";import{f as M,i as z,m as y,d as B,u as T,a as b,c as C,k as S,h as g,j as _,b as H,E as w,z as O,A as j,I as x,H as u,C as I,B as P}from"./index.b56e0f28.js";import"./use-rtl.e209f69a.js";import"./Eye.c14419e2.js";import"./Button.77702d3b.js";var L={headerFontSize1:"30px",headerFontSize2:"22px",headerFontSize3:"18px",headerFontSize4:"16px",headerFontSize5:"16px",headerFontSize6:"16px",headerMargin1:"28px 0 20px 0",headerMargin2:"28px 0 20px 0",headerMargin3:"28px 0 20px 0",headerMargin4:"28px 0 18px 0",headerMargin5:"28px 0 18px 0",headerMargin6:"28px 0 18px 0",headerPrefixWidth1:"16px",headerPrefixWidth2:"16px",headerPrefixWidth3:"12px",headerPrefixWidth4:"12px",headerPrefixWidth5:"12px",headerPrefixWidth6:"12px",headerBarWidth1:"4px",headerBarWidth2:"4px",headerBarWidth3:"3px",headerBarWidth4:"3px",headerBarWidth5:"3px",headerBarWidth6:"3px",pMargin:"16px 0 16px 0",liMargin:".25em 0 0 0",olPadding:"0 0 0 2em",ulPadding:"0 0 0 2em"};const E=e=>{const{primaryColor:n,textColor2:t,borderColor:i,lineHeight:r,fontSize:o,borderRadiusSmall:a,dividerColor:s,fontWeightStrong:m,textColor1:l,textColor3:d,infoColor:c,warningColor:h,errorColor:p,successColor:f,codeColor:v}=e;return Object.assign(Object.assign({},L),{aTextColor:n,blockquoteTextColor:t,blockquotePrefixColor:i,blockquoteLineHeight:r,blockquoteFontSize:o,codeBorderRadius:a,liTextColor:t,liLineHeight:r,liFontSize:o,hrColor:s,headerFontWeight:m,headerTextColor:l,pTextColor:t,pTextColor1Depth:l,pTextColor2Depth:t,pTextColor3Depth:d,pLineHeight:r,pFontSize:o,headerBarColor:n,headerBarColorPrimary:n,headerBarColorInfo:c,headerBarColorError:p,headerBarColorWarning:h,headerBarColorSuccess:f,textColor:t,textColor1Depth:l,textColor2Depth:t,textColor3Depth:d,textColorPrimary:n,textColorInfo:c,textColorSuccess:f,textColorWarning:h,textColorError:p,codeTextColor:t,codeColor:v,codeBorder:"1px solid #0000"})},N={name:"Typography",common:M,self:E};var W=N,q=z("p",`
 box-sizing: border-box;
 transition: color .3s var(--n-bezier);
 margin: var(--n-margin);
 font-size: var(--n-font-size);
 line-height: var(--n-line-height);
 color: var(--n-text-color);
`,[y("&:first-child","margin-top: 0;"),y("&:last-child","margin-bottom: 0;")]);const K=Object.assign(Object.assign({},b.props),{depth:[String,Number]});var A=B({name:"P",props:K,setup(e){const{mergedClsPrefixRef:n,inlineThemeDisabled:t}=T(e),i=b("Typography","-p",q,W,e,n),r=C(()=>{const{depth:a}=e,s=a||"1",{common:{cubicBezierEaseInOut:m},self:{pFontSize:l,pLineHeight:d,pMargin:c,pTextColor:h,[`pTextColor${s}Depth`]:p}}=i.value;return{"--n-bezier":m,"--n-font-size":l,"--n-line-height":d,"--n-margin":c,"--n-text-color":a===void 0?h:p}}),o=t?S("p",C(()=>`${e.depth||""}`),r,e):void 0;return{mergedClsPrefix:n,cssVars:t?void 0:r,themeClass:o==null?void 0:o.themeClass,onRender:o==null?void 0:o.onRender}},render(){var e;return(e=this.onRender)===null||e===void 0||e.call(this),g("p",{class:[`${this.mergedClsPrefix}-p`,this.themeClass],style:this.cssVars},this.$slots)}}),G=z("text",`
 transition: color .3s var(--n-bezier);
 color: var(--n-text-color);
`,[_("strong",`
 font-weight: var(--n-font-weight-strong);
 `),_("italic",{fontStyle:"italic"}),_("underline",{textDecoration:"underline"}),_("code",`
 line-height: 1.4;
 display: inline-block;
 font-family: var(--n-font-famliy-mono);
 transition: 
 color .3s var(--n-bezier),
 border-color .3s var(--n-bezier),
 background-color .3s var(--n-bezier);
 box-sizing: border-box;
 padding: .05em .35em 0 .35em;
 border-radius: var(--n-code-border-radius);
 font-size: .9em;
 color: var(--n-code-text-color);
 background-color: var(--n-code-color);
 border: var(--n-code-border);
 `)]);const J=Object.assign(Object.assign({},b.props),{code:Boolean,type:{type:String,default:"default"},delete:Boolean,strong:Boolean,italic:Boolean,underline:Boolean,depth:[String,Number],tag:String,as:{type:String,validator:()=>!0,default:void 0}});var Q=B({name:"Text",props:J,setup(e){const{mergedClsPrefixRef:n,inlineThemeDisabled:t}=T(e),i=b("Typography","-text",G,W,e,n),r=C(()=>{const{depth:a,type:s}=e,m=s==="default"?a===void 0?"textColor":`textColor${a}Depth`:H("textColor",s),{common:{fontWeightStrong:l,fontFamilyMono:d,cubicBezierEaseInOut:c},self:{codeTextColor:h,codeBorderRadius:p,codeColor:f,codeBorder:v,[m]:$}}=i.value;return{"--n-bezier":c,"--n-text-color":$,"--n-font-weight-strong":l,"--n-font-famliy-mono":d,"--n-code-border-radius":p,"--n-code-text-color":h,"--n-code-color":f,"--n-code-border":v}}),o=t?S("text",C(()=>`${e.type[0]}${e.depth||""}`),r,e):void 0;return{mergedClsPrefix:n,compitableTag:D(e,["as","tag"]),cssVars:t?void 0:r,themeClass:o==null?void 0:o.themeClass,onRender:o==null?void 0:o.onRender}},render(){var e,n,t;const{mergedClsPrefix:i}=this;(e=this.onRender)===null||e===void 0||e.call(this);const r=[`${i}-text`,this.themeClass,{[`${i}-text--code`]:this.code,[`${i}-text--delete`]:this.delete,[`${i}-text--strong`]:this.strong,[`${i}-text--italic`]:this.italic,[`${i}-text--underline`]:this.underline}],o=(t=(n=this.$slots).default)===null||t===void 0?void 0:t.call(n);return this.code?g("code",{class:r,style:this.cssVars},this.delete?g("del",null,o):o):this.delete?g("del",{class:r,style:this.cssVars},o):g(this.compitableTag||"span",{class:r,style:this.cssVars},o)}});const U={},X={class:"home-box"},Y={style:{"margin-bottom":"12px"}},Z=P(" \u70B9\u51FB\u6216\u8005\u62D6\u52A8\u6587\u4EF6\u5230\u8BE5\u533A\u57DF\u6765\u4E0A\u4F20 "),ee=P(" \u8BF7\u4E0D\u8981\u4E0A\u4F20\u654F\u611F\u6570\u636E\uFF0C\u6BD4\u5982\u4F60\u7684\u94F6\u884C\u5361\u53F7\u548C\u5BC6\u7801\uFF0C\u4FE1\u7528\u5361\u53F7\u6709\u6548\u671F\u548C\u5B89\u5168\u7801 ");function oe(e,n){const t=w("archive-icon"),i=F,r=Q,o=A,a=k,s=V;return O(),j("div",X,[x(s,{action:"https://www.mocky.io/v2/5e4bafc63100007100d8b70f"},{default:u(()=>[x(a,null,{default:u(()=>[I("div",Y,[x(i,{size:"70",depth:3},{default:u(()=>[x(t)]),_:1})]),x(r,{style:{"font-size":"16px"}},{default:u(()=>[Z]),_:1}),x(o,{depth:"3",style:{margin:"8px 0 0 0"}},{default:u(()=>[ee]),_:1})]),_:1})]),_:1})])}var de=R(U,[["render",oe],["__scopeId","data-v-20fcc2b6"]]);export{de as default};