Date.prototype.format = function(fmt = "YYYY-MM-DD HH:mm:ss"): string {
  let o: {
    [key: string]: number
  }
  o = {
    "M+": this.getMonth() + 1,                    // 月份
    "D+": this.getDate(),                         // 日
    "H+": this.getHours(),                        // 小时
    "m+": this.getMinutes(),                      // 分
    "s+": this.getSeconds(),                      // 秒
    "q+": Math.floor((this.getMonth() + 3) / 3),  // 季度
    "S": this.getMilliseconds()                   // 毫秒
  };
  if (/(Y+)/.test(fmt))
    fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (let k in o)
    if (new RegExp("(" + k + ")").test(fmt))
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? String((o[k])) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}

const format = function(times: Date, pattern = "YYYY-MM-DD HH:mm:ss") {
  if (!times) return null;
  var d = null;
  d = new Date(times).format(pattern)
  return d.toLocaleString();
}

export default format