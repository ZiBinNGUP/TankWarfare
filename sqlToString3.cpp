#include <iostream>
#include <string.h>
using namespace std;
string sql;  //����sql��������
string str_sql;//����string���͵�sql��� 
string temp;  //������ʱ���� 

int len_sql; //����sql�ĳ���
int i = 0;//������ 

bool variable = false; //ֵΪtrue���ַ�Ϊ������һ���� 
bool first;
bool insert_sql = false;//insert sql��� 

bool isVariable();   //�ж��Ƿ�Ϊ���� 
int main()
{
//	freopen("source.txt","r",stdin);
//	freopen("answer.txt","w",stdout);
	int c;   //����������ȡsql��� �����䱣�浽sql�� 
	while(scanf("%c",&c) != EOF)
	{
		c = tolower(c);
		sql += c;
	}
	
	len_sql = sql.length(); //��ȡsql���� 
	
	while(1)
	{
		if(sql[i] == ' ')  //ȥ������ո� 
		{
			i++;
			continue;
		}
		if(sql[i] == 'i')insert_sql = true;  //�ж��Ƿ�Ϊinsert��� 
		str_sql += "\"";
		str_sql += sql[i++];
		for(;i < len_sql;i++)          //����sql 
		{
			if(sql[i] == '(' || sql[i] == '=') //���sql[i]Ϊ ( �� = ʱ 
			{
				str_sql += sql[i];
				variable = true;
				first = true;
			}
			else if(isVariable())     // �ж�sql[i]�Ƿ�Ϊ���� 
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
			else if(sql[i] == 39)    //�ж�sql[i]�Ƿ�Ϊ������' 
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

