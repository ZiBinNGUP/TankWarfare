#include <iostream>
#include <string.h>
using namespace std;
string sql;  //保存sql语句的内容
string str_sql;//保存string类型的sql语句 
string temp;  //保存临时变量 

int len_sql; //保存sql的长度
int i = 0;//迭代器 

bool variable = false; //值为true该字符为变量的一部分 
bool first;
bool insert_sql = false;//insert sql语句 

bool isVariable();   //判断是否为变量 
int main()
{
//	freopen("source.txt","r",stdin);
//	freopen("answer.txt","w",stdout);
	int c;   //用来辅助读取sql语句 并将其保存到sql中 
	while(scanf("%c",&c) != EOF)
	{
		c = tolower(c);
		sql += c;
	}
	
	len_sql = sql.length(); //获取sql长度 
	
	while(1)
	{
		if(sql[i] == ' ')  //去除多余空格 
		{
			i++;
			continue;
		}
		if(sql[i] == 'i')insert_sql = true;  //判断是否为insert语句 
		str_sql += "\"";
		str_sql += sql[i++];
		for(;i < len_sql;i++)          //遍历sql 
		{
			if(sql[i] == '(' || sql[i] == '=') //如果sql[i]为 ( 或 = 时 
			{
				str_sql += sql[i];
				variable = true;
				first = true;
			}
			else if(isVariable())     // 判断sql[i]是否为变量 
			{
				if(first == true)
				{
					str_sql += temp + "\" + ";
//					str_sql += ;
					temp.clear();
					first = false;
				}
				str_sql += sql[i];
			}
			else if(sql[i] == 39)    //判断sql[i]是否为单引号' 
			{
				temp += sql[i];
				if(i == len_sql - 1 || sql[i+1] == ';')
				{
					str_sql += " +\"" + temp + "\"";
//					str_sql += 
//					str_sql += ;
				}
			}
			else if((sql[i] == ',' || sql[i] == ')'||sql[i] == ' ') && variable && !first) 
			{
				str_sql += " + \"" + temp + sql[i];
//				str_sql +=;
//				str_sql +=;
				first = true;
				if(!insert_sql)
				variable = false;
				temp.clear();
				if(sql[i] == ')')str_sql += "\"";
			}
			else
			{
				str_sql += sql[i];
			}
		}
		break;	
	}
	cout<<str_sql;
	return 0;
 }
 
bool isVariable()
{
	return (variable && sql[i] >= 'a' && sql[i] <= 'z') || sql[i] == '_' || (sql[i] >= '0' && sql[i] <= '9');
}

