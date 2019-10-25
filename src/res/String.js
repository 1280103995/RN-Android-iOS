function insert_item(str,item,index){

var newstr="";             //初始化一个空字符串

var tmp=str.substring(0,index);

var estr=str.substring(index,str.length);

newstr+=tmp+item+estr;

return newstr;

}