// 首字母转小写
export function firstToUpper1 (str) {
  if (!str) return str
  return str.substring(0, 1).toUpperCase() + str.substring(1)
}

// 获取url 参数
export function getQueryVariable(variable)
{
  var query = window.location.search.substring(1);
  var vars = query.split("&");
  for (var i=0;i<vars.length;i++) {
          var pair = vars[i].split("=");
          if(pair[0] == variable){return pair[1];}
  }
  return(false);
}

// 格式化日期
export function dateFormat1(date, fmt) {

  //获取年份
  if (/(y+)/.test(fmt)) {
      // 把数字变成字符串
      let dateY = date.getFullYear() + "";
      //RegExp.$1 在判断中出现过，且是括号括起来的，所以 RegExp.$1 就是 "yyyy"
      fmt = fmt.replace(RegExp.$1, dateY.substr(4 - RegExp.$1.length));
  }

  //获取其他
  let o = {
      "M+": date.getMonth() + 1,
      "d+": date.getDate(),
      "h+": date.getHours(),
      "m+": date.getMinutes(),
      "s+": date.getSeconds(),
  };
  for (const k in o) {
      if (new RegExp(`(${k})`).test(fmt)) {
          let str = o[k] + "";
          fmt = fmt.replace(
              RegExp.$1,
              RegExp.$1.length == 1 ? str : padLeftZero(str)
          );
      }
  }
  return fmt;
}

function padLeftZero(str) {
  return ("00" + str).substr(str.length);
}