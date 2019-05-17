//import Your database connection class

import java.security.Timestamp;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
// Created By Naveen Roy : 06-06-2018
/**
 * Description : Class for create parameters
 * @author Naveen Roy
 */
class Parameter{
	private String dataType;
	private String value;
	
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
/**
 * @Description : Database Query Builder
 * @author Naveen Roy
 * @LastUpdated : 26-06-2018
 */

public class DBQuery {

	Connection con = null;
	private String FullQuery = "";
	private String BatchQuery = "";
	private static DBQuery instance = null;
	public Boolean showParameter = false;
	private String orwhere = "";
	private String where = "";
	private String coloms = "";
	private String strString = "";
	private String from = "";
	private String insert = "";
	private String update = "";
	private String groupby = "";
	private String orderby = "";
	private List<Parameter> list = new ArrayList<Parameter>();
	private List<Parameter> Batchlist = new ArrayList<Parameter>();
	Parameter param;
	
	PreparedStatement pstmt = null;
    ResultSet rs = null;
	
	
	public DBQuery() {
		con = DBUtil.getConnection(); //Your Database class get connection
	}
	
	public DBQuery(Connection conn) {
		// TODO Auto-generated constructor stub
		this.con = conn;
	}

	public void autoCommit(Boolean value){
		try {
			con.setAutoCommit(value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void commit(){
		try {
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Commit Failed : "+ e.getMessage());
		}
	}

	public static DBQuery getInstance(){
		if(instance==null){
			instance = new DBQuery();
		}
		return instance;
	}
	
	public Connection getConnection() {
		return con;
	}
	
	public static String rowCount(String Query){
		Query = "SELECT COUNT(*) AS ROW_COUNT FROM(" + Query + ")";
		return Query;
	}
	
	public DBQuery count(){
		FullQuery = "SELECT COUNT(*) AS ROW_COUNT FROM(" + FullQuery + ")";
		return this;
	}
	
	public DBQuery limit(long i) {
		FullQuery = "SELECT * FROM(" + FullQuery + ") where ROWNUM <= " + i;
		return this;
	}
	
	
	public DBQuery insert(String ColumnName,String value){
		if(insert.length()>0)
			insert = ", " + ColumnName;
		else
			insert = ColumnName;
		FullQuery = FullQuery + insert;
		setParam("String",value);
		return this;
	}
	
	public DBQuery insertStr(String ColumnName,String value){
		if(insert.length()>0)
			insert = ", " + ColumnName;
		else
			insert = ColumnName;
		FullQuery = FullQuery + insert;
		setParam("strString",value);
		return this;
	}
	


	

	
	public DBQuery insert(String ColumnName,int value){
		if(insert.length()>0)
			insert = ", " + ColumnName;
		else
			insert = ColumnName;
		FullQuery = FullQuery + insert;
		setParam("String", value+"");
		return this;
	}
	public DBQuery insert(String ColumnName,long value){
		if(insert.length()>0)
			insert = ", " + ColumnName;
		else
			insert = ColumnName;
		FullQuery = FullQuery + insert;
		setParam("String",value+"");
		return this;
	}
	public DBQuery insert(String ColumnName,float value){
		if(insert.length()>0)
			insert = ", " + ColumnName;
		else
			insert = ColumnName;
		FullQuery = FullQuery + insert;
		setParam("String",value+"");
		return this;
	}
	public DBQuery insert(String ColumnName,double value){
		if(insert.length()>0)
			insert =  ", " + ColumnName;
		else
			insert = ColumnName;
		FullQuery = FullQuery + insert;
		setParam("String",value+"");
		return this;
	}
	
	public DBQuery into(String tableName){
		FullQuery = "INSERT INTO " + tableName + "(" + FullQuery +") ";
		String str = "";
		for(int i=0 ; i< list.size();i++){
			param =list.get(i);
			if(str.length()>0){
				if(param.getDataType()=="strString"){
					str = str + "," + param.getValue();
				}else{
					str = str + ", ?";
				}
				
			}
			else{
				if(param.getDataType()=="strString"){
					str = param.getValue();
				}
				else{
					str = "?";
				}
				
			}
				
		}		
		FullQuery = FullQuery + "VALUES(" + str + ")";
		return this;
	}
	
	
	public DBQuery delete(String tableName){
		FullQuery = "DELETE FROM "+ tableName + " ";
		return this;
	}
	
	
	
	public DBQuery update(String tableName){
		FullQuery = "UPDATE " + tableName + " SET ";
		return this;
	}
	public DBQuery set(String colomnName,String value){
		
		if(update.length()>0)
			update = ", " + colomnName + " = ? ";
		else
			update = colomnName + " = ? ";
		
		FullQuery = FullQuery + update;
		setParam("String",value+"");
		return this;
	}
	public DBQuery set(String colomnName,int value){
		
		if(update.length()>0)
			update = ", " + colomnName + " = ? ";
		else
			update = colomnName + " = ? ";
		
		FullQuery = FullQuery + update;
		setParam("int",value+"");
		return this;
	}
	public DBQuery set(String colomnName,float value){
			
			if(update.length()>0)
				update = ", " + colomnName + " = ? ";
			else
				update = colomnName + " = ? ";
			
			FullQuery = FullQuery + update;
			setParam("float",value+"");
			return this;
		}
	public DBQuery set(String colomnName,long value){
		
		if(update.length()>0)
			update = ", " + colomnName + " = ? ";
		else
			update = colomnName + " = ? ";
		
		FullQuery = FullQuery + update;
		setParam("long",value+"");
		return this;
	}
	public DBQuery set(String colomnName,double value){
		
		if(update.length()>0)
			update = ", " + colomnName + " = ? ";
		else
			update = colomnName + " = ? ";
		
		FullQuery = FullQuery + update;
		setParam("double",value+"");
		return this;
	}
	public DBQuery set(String colomnName,Timestamp value){
		
		if(update.length()>0)
			update = ", " + colomnName + " = ? ";
		else
			update = colomnName + " = ? ";
		
		FullQuery = FullQuery + update;
		setParam("time",value+"");
		return this;
	}
	
public DBQuery setStr(String colomnName,String value){
		
		if(update.length()>0){
			update = ", " + colomnName + " = "+ value + " ";
		}
		else{
			update = colomnName + " = " + value + " ";
		}
		FullQuery = FullQuery + update;
		return this;
	}
	
	
	
	
	
	
	
	
	
	
	public static String paginate(String Query,long startIndex,int pageRange){
		int index = 0;
		index = Query.indexOf("FROM");
		Query =  new StringBuilder(Query).insert((index - 1), ", ROWNUM RN ").toString();
		Query = "SELECT * FROM("+ Query + ", ROWNUM ASC)WHERE RN<"+ (startIndex+pageRange+1) +" AND RN>"+ startIndex;
		return Query;
	}
	public static String orderByPaginate(String Query,long startIndex,int pageRange){
		int index = 0;
		index = Query.indexOf("FROM");
		Query =  new StringBuilder(Query).insert((index - 1), ", ROWNUM RN ").toString();
		Query = "SELECT * FROM("+ Query + "ORDER BY ROWNUM ASC)WHERE RN <"+ (startIndex+pageRange+1) +" AND RN>"+ startIndex;
		return Query;
	}
	public DBQuery paginate(long startIndex,int pageRange){
		int index = 0;
		index = FullQuery.indexOf("FROM");
		FullQuery =  new StringBuilder(FullQuery).insert((index - 1), ", ROWNUM RN ").toString();
		if(orderby.length() > 0){
			FullQuery= "SELECT * FROM("+ FullQuery + ", ROWNUM ASC)WHERE RN<"+ (startIndex+pageRange+1) +" AND RN>"+ startIndex;
		}
		else{
			FullQuery = "SELECT * FROM("+ FullQuery + "ORDER BY ROWNUM ASC)WHERE RN <"+ (startIndex+pageRange+1) +" AND RN>"+ startIndex;
		}
		return this;
	}
	
	
	public DBQuery select(){
		where = "";
		from = "";
		FullQuery = FullQuery + "SELECT * ";
		
		return this;
	}
	
	public DBQuery select(String ColumnName){
		where = "";
		from = "";
		if(coloms.length() > 0)
			coloms = ","+ColumnName+" ";
		else
			coloms = "SELECT "+ ColumnName+ " ";
		FullQuery = FullQuery + coloms;
		return this;
	}
	
	public DBQuery selectNested(String ColumnName){
		coloms = "";
		if(coloms.length() > 0)
			coloms = ","+ColumnName+" ";
		else
			coloms = ",(SELECT "+ ColumnName+ " ";
		FullQuery = FullQuery + coloms;
		return this;
	}
	
	// String Parameter processing ---------------------------------------------------------------
	public DBQuery whereStr(String ColumnName,String Condition,String compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " " + Condition + " " + compareWith + " ";
		}else{
			where = "WHERE " + ColumnName + " " + Condition + " " + compareWith+ " ";
		}
		FullQuery = FullQuery + where;
		return this;
	}
	public DBQuery whereStr(String ColumnName,String compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " = " + compareWith + " ";
		}else{
			where = "WHERE " + ColumnName + " = " + compareWith+ " ";
		}
		FullQuery = FullQuery + where;
		return this;
	}
	public DBQuery orWhereStr(String ColumnName,String Condition,String compareWith){
		if(where.length() > 0){
			where = "OR " + ColumnName + " " + Condition + " " + compareWith + " ";
		}else{
			where = "WHERE " + ColumnName + " " + Condition + " " + compareWith+ " ";
		}
		FullQuery = FullQuery + where;
		return this;
	}
	public DBQuery orWhereStr(String ColumnName,String compareWith){
		if(where.length() > 0){
			where = "OR " + ColumnName + " = " + compareWith + " ";
		}else{
			where = "WHERE " + ColumnName + " = " + compareWith+ " ";
		}
		FullQuery = FullQuery + where;
		return this;
	}
	
	public DBQuery where(String ColumnName,String Condition,String compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " " + Condition + " ? ";
		}else{
			where = "WHERE " + ColumnName + " " + Condition + " ? ";
		}
		FullQuery = FullQuery + where;
		setParam("String",compareWith);
		return this;
	}
	public DBQuery where(String ColumnName,String compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " = ? ";
		}else{
			where = "WHERE " + ColumnName + " =  ?  ";
		}
		FullQuery = FullQuery + where;
		setParam("String",compareWith);
		return this;
	}

	
	public DBQuery orWhere(String ColumnName,String Condition,String compareWith){
		
		orwhere = "OR " + ColumnName + " " + Condition + "  ?  ";
		FullQuery = FullQuery + orwhere;
		setParam("String",compareWith);
		return this;
	}
	public DBQuery orWhere(String ColumnName,String compareWith){
		
		orwhere = "OR " + ColumnName + " =  ?  ";
		FullQuery = FullQuery + orwhere;
		setParam("String",compareWith);
		return this;
	}
	
	public DBQuery inWhere(String ColumnName,String compareWith){
		if(where.length()>0){
			where = "AND " + ColumnName + " IN ( ? ) ";
		}else{
			where = "WHERE " + ColumnName + " IN ( ? ) ";
		}
		
		FullQuery = FullQuery + where;
		setParam("String",compareWith);
		return this;
	}
	public DBQuery orInWhere(String ColumnName,String compareWith){
		if(where.length()>0){
			where = "OR " + ColumnName + " IN ( ? ) ";
		}else{
			where = "WHERE " + ColumnName + " IN ( ? ) ";
		}
		
		FullQuery = FullQuery + where;
		setParam("String",compareWith);
		
		return this;
	}
	public DBQuery likeWhere(String ColumnName,String compareWith){
		if(where.length()>0){
			where = "AND UPPER(" + ColumnName + ") LIKE UPPER("+ compareWith +") ";
		}else{
			where = "WHERE UPPER(" + ColumnName + ") LIKE UPPER("+ compareWith +") ";
		}
		FullQuery = FullQuery + where;
	
	
		return this;
	}
	public DBQuery orLikeWhere(String ColumnName,String compareWith){
		if(where.length()>0){
			where = "OR UPPER(" + ColumnName + ") LIKE UPPER("+ compareWith +") ";
		}else{
			where = "WHERE UPPER(" + ColumnName + ") LIKE UPPER("+ compareWith +") ";
		}
		
		FullQuery = FullQuery + where;
		
		
		return this;
	}
	
	
	// int Parameter processing -----------------------------------------------------------
	
	public DBQuery where(String ColumnName,String Condition,int compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " " + Condition + "  ?  ";
		}else{
			where = "WHERE " + ColumnName + " " + Condition + "  ?  ";
		}
		FullQuery = FullQuery + where;
		setParam("int",compareWith+"");
		return this;
	}
	public DBQuery where(String ColumnName,int  compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " =  ?  ";
		}else{
			where = "WHERE " + ColumnName + " =  ?  ";
		}
		FullQuery = FullQuery + where;
		setParam("int",compareWith+"");
		return this;
	}

	
	public DBQuery orWhere(String ColumnName,String Condition,int  compareWith){
		
		orwhere = "OR " + ColumnName + " " + Condition + "  ?  ";
		FullQuery = FullQuery + orwhere;
		setParam("int",compareWith+"");
		return this;
	}
	public DBQuery orWhere(String ColumnName,int  compareWith){
		
		orwhere = "OR " + ColumnName + " =  ?  ";
		FullQuery = FullQuery + orwhere;
		setParam("int",compareWith+"");
		return this;
	}
	
	public DBQuery inWhere(String ColumnName,int compareWith){
		if(where.length()>0){
			where = "AND " + ColumnName + " IN ( ? ) ";
		}else{
			where = "WHERE " + ColumnName + " IN ( ? ) ";
		}
		
		FullQuery = FullQuery + where;
		setParam("int",compareWith+"");
		return this;
	}

	
	
	// long Parameter processing ---------------------------------------------------------
	
	public DBQuery where(String ColumnName,String Condition,long compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " " + Condition + "  ?  ";
		}else{
			where = "WHERE " + ColumnName + " " + Condition + "  ?  ";
		}
		FullQuery = FullQuery + where;
		setParam("long",compareWith+"");
		return this;
	}
	public DBQuery where(String ColumnName,long  compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " =  ?  ";
		}else{
			where = "WHERE " + ColumnName + " =  ?  ";
		}
		FullQuery = FullQuery + where;
		setParam("long",compareWith+"");
		return this;
	}

	
	public DBQuery orWhere(String ColumnName,String Condition,long  compareWith){
		
		orwhere = "OR " + ColumnName + " " + Condition + "  ?  ";
		FullQuery = FullQuery + orwhere;
		setParam("long",compareWith+"");
		return this;
	}
	public DBQuery orWhere(String ColumnName,long  compareWith){
		
		orwhere = "OR " + ColumnName + " =  ?  ";
		FullQuery = FullQuery + orwhere;
		setParam("long",compareWith+"");
		return this;
	}
	
	public DBQuery inWhere(String ColumnName,long compareWith){
		if(where.length()>0){
			where = "AND " + ColumnName + " IN ( ? ) ";
		}else{
			where = "WHERE " + ColumnName + " IN ( ? ) ";
		}
		
		FullQuery = FullQuery + where;
		setParam("long",compareWith+"");
		return this;
	}

	
	
	
	// float Parameter processing ------------------------------------------------
	
	public DBQuery where(String ColumnName,String Condition,float compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " " + Condition + "  ?  ";
		}else{
			where = "WHERE " + ColumnName + " " + Condition + "  ?  ";
		}
		FullQuery = FullQuery + where;
		setParam("float",compareWith+"");
		return this;
	}
	public DBQuery where(String ColumnName,float  compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " =  ?  ";
		}else{
			where = "WHERE " + ColumnName + " =  ?  ";
		}
		FullQuery = FullQuery + where;
		setParam("float",compareWith+"");
		return this;
	}

	
	public DBQuery orWhere(String ColumnName,String Condition,float  compareWith){
		
		orwhere = "OR " + ColumnName + " " + Condition + "  ?  ";
		FullQuery = FullQuery + orwhere;
		setParam("float",compareWith+"");
		return this;
	}
	public DBQuery orWhere(String ColumnName,float  compareWith){
		
		orwhere = "OR " + ColumnName + " =  ?  ";
		FullQuery = FullQuery + orwhere;
		setParam("float",compareWith+"");
		return this;
	}
	
	public DBQuery inWhere(String ColumnName,float compareWith){
		if(where.length()>0){
			where = "AND " + ColumnName + " IN ( ? ) ";
		}else{
			where = "WHERE " + ColumnName + " IN ( ? ) ";
		}
		
		FullQuery = FullQuery + where;
		setParam("float",compareWith+"");
		return this;
	}

	
	// double Parameter processing ---------------------------------------------------
	
	public DBQuery where(String ColumnName,String Condition,double compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " " + Condition + "  ?  ";
		}else{
			where = "WHERE " + ColumnName + " " + Condition + "  ?  ";
		}
		FullQuery = FullQuery + where;
		setParam("double",compareWith+"");
		return this;
	}
	public DBQuery where(String ColumnName,double  compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " =  ?  ";
		}else{
			where = "WHERE " + ColumnName + " =  ?  ";
		}
		FullQuery = FullQuery + where;
		setParam("double",compareWith+"");
		return this;
	}

	
	public DBQuery orWhere(String ColumnName,String Condition,double  compareWith){
		
		orwhere = "OR " + ColumnName + " " + Condition + "  ?  ";
		FullQuery = FullQuery + orwhere;
		setParam("double",compareWith+"");
		return this;
	}
	public DBQuery orWhere(String ColumnName,double compareWith){
		
		orwhere = "OR " + ColumnName + " =  ?  ";
		FullQuery = FullQuery + orwhere;
		setParam("double",compareWith+"");
		return this;
	}
	
	public DBQuery inWhere(String ColumnName,double compareWith){
		if(where.length()>0){
			where = "AND " + ColumnName + " IN ( ? ) ";
		}else{
			where = "WHERE " + ColumnName + " IN ( ? ) ";
		}
		
		FullQuery = FullQuery + where;
		setParam("double",compareWith+"");
		return this;
	}

	//Date parameter processing
	
	public DBQuery between(String ColumnName,Date startDate,Date endDate){
		if(where.length()>0){
			where = "AND (" + ColumnName + " BETWEEN TO_DATE(" + startDate + ") AND TO_DATE("+ endDate +")) ";
		}else{
			where = "WHERE (" + ColumnName + " BETWEEN TO_DATE(" + startDate + ") AND TO_DATE("+ endDate +")) ";
		}
		FullQuery = FullQuery + where;
		setParam("date",startDate+"");
		setParam("date",endDate+"");
		return this;
	}
	public DBQuery orBetween(String ColumnName,Date startDate,Date endDate){
		if(where.length()>0){
			where = "OR (" + ColumnName + " BETWEEN TO_DATE(?) AND TO_DATE(?)) ";
		}else{
			where = "WHERE (" + ColumnName + " BETWEEN TO_DATE(?) AND TO_DATE(?)) ";
		}
		FullQuery = FullQuery + where;
		setParam("date",startDate+"");
		setParam("date",endDate+"");
		return this;
	}
	
	
	public DBQuery where(String ColumnName,String Condition,Date compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " " + Condition + " TO_DATE(?) ";
		}else{
			where = "WHERE " + ColumnName + " " + Condition + " TO_DATE(?) ";
		}
		FullQuery = FullQuery + where;
		setParam("date",compareWith+"");
		return this;
	}
	public DBQuery where(String ColumnName,Date compareWith){
		if(where.length() > 0){
			where = "AND " + ColumnName + " =  TO_DATE(?) ";
		}else{
			where = "WHERE " + ColumnName + " =  TO_DATE(?)  ";
		}
		FullQuery = FullQuery + where;
		setParam("date",compareWith+"");
		return this;
	}

	
	public DBQuery orWhere(String ColumnName,String Condition,Date compareWith){
		
		orwhere = "OR " + ColumnName + " " + Condition + " TO_DATE(?)  ";
		FullQuery = FullQuery + orwhere;
		setParam("date",compareWith+"");
		return this;
	}
	public DBQuery orWhere(String ColumnName,Date compareWith){
		
		orwhere = "OR " + ColumnName + " = TO_DATE(?) ";
		FullQuery = FullQuery + orwhere;
		setParam("date",compareWith+"");
		return this;
	}
	
	
	//-------------------------------------------------------------------------
	public DBQuery put(String str){
		FullQuery = FullQuery + str + " ";
		return this;
	}
	
	public DBQuery groupBy(String colomnName){
		
		if(groupby.length()>0)
			groupby = ", " + colomnName + " ";
		else{
			groupby = "GROUP BY " + colomnName + " ";
		}
		
		FullQuery = FullQuery + groupby;
	
		return this;
	}
	
	public DBQuery orderBy(String colomnName, String reverse){
		
		if(orderby.length()>0)
			orderby = ", " + colomnName + " "+ reverse +" ";
		else{
			orderby = "ORDER BY " + colomnName + " "+ reverse +" ";
		}

		FullQuery = FullQuery + orderby;
		return this;
	}
	public DBQuery orderBy(String colomnName){
		if(orderby.length()>0)
			orderby = ", " + colomnName + " ";
		else
			orderby = "ORDER BY " + colomnName + " ";
		
		FullQuery = FullQuery + orderby;
		return this;
	}
	
	
	
	public DBQuery from(String tableNames){
		where = "";
		if(from.length() > 0)
			from = "," + tableNames+ " ";
		else
			from = "FROM " + tableNames+ " ";
		FullQuery = FullQuery + from;
		return this;
	}
	
	public DBQuery extend(String Query){
		FullQuery = FullQuery + Query;
		return this;
	}
	
	public Boolean execute() throws SQLException {
		Boolean res;
		int update; 
		int j=1;

		try{
			pstmt = con.prepareStatement(FullQuery);
			for(int i=0;i<list.size();i++ ){
				param = new Parameter();
				param =list.get(i);
				if(param.getDataType()=="String"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					
					pstmt.setString(j++,param.getValue());
					
				}else if(param.getDataType()=="int"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setInt(j++,Integer.parseInt(param.getValue()));
					
				}else if(param.getDataType()=="long"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setLong(j++,Long.parseLong(param.getValue()));
					
				}else if(param.getDataType()=="float"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setFloat(j++,Float.parseFloat(param.getValue()));
					
				}else if(param.getDataType()=="double"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setDouble(j++,Double.parseDouble(param.getValue()));
					
				}else if(param.getDataType()=="time"){	
					 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    			    java.util.Date parsedDate =  dateFormat.parse(param.getValue());
    			    java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
    			    if(showParameter)
						System.out.println(j + " : " + timestamp);
					pstmt.setTimestamp(j++,timestamp);
					
				}else if(param.getDataType()=="strString"){
					
				}
				else{
					System.out.println("Error Parsing Parameter");
				}
			}
			update = pstmt.executeUpdate();
			res = true;
		}
		catch(Exception ex){
			System.out.println("Error executing Query " + ex.getMessage());
			System.out.println(FullQuery);
			res = false;
		}
		return res;
	}
	
	
	public DBQuery addBatch(){
		BatchQuery = BatchQuery + FullQuery + ";";
		Batchlist.addAll(list);
		FullQuery = "";
		list.clear();
		orwhere = "";
		where = "";
		coloms = "";
		strString = "";
		from = "";
		insert = "";
		update = "";
		groupby = "";
		orderby = "";
		return this;
	}
	
	public Boolean executeBatch(){
		Boolean res = false;
		FullQuery = BatchQuery;
		list.addAll(Batchlist);
		try {
			 res = this.execute();
		} catch (SQLException e) {
			System.out.println("Error Executing Batch : "+ e.getMessage());
		}
		return res;
	}

	public ResultSet get() throws SQLException{
		int j=1;
		try{

			pstmt = con.prepareStatement(FullQuery);
			for(int i=0;i<list.size();i++ ){
				param = new Parameter();
				param =list.get(i);
				if(param.getDataType()=="String"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setString(j++,param.getValue());
					
				}else if(param.getDataType()=="int"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setInt(j++,Integer.parseInt(param.getValue()));
				
				}else if(param.getDataType()=="long"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setLong(j++,Long.parseLong(param.getValue()));
					
				}else if(param.getDataType()=="float"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setFloat(j++,Float.parseFloat(param.getValue()));
				
				}else if(param.getDataType()=="double"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setDouble(j++,Double.parseDouble(param.getValue()));
				
				}else if(param.getDataType()=="time"){	
					 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    			    java.util.Date parsedDate =  dateFormat.parse(param.getValue());
    			    java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
    			    if(showParameter)
						System.out.println(j + " : " + timestamp);
					pstmt.setTimestamp(j++,timestamp);
				}else{
					System.out.println("Error Parsing Parameter");
				}
			}
			rs = pstmt.executeQuery();
		}
		catch(Exception ex){
			System.out.println("Error In Query :" + ex.getMessage());
			System.out.println(FullQuery);
		}
		return rs;
	}
	public ResultSet first() throws SQLException{
		int j=1;
		try{

			pstmt = con.prepareStatement(FullQuery);
			for(int i=0;i<list.size();i++ ){
				param = new Parameter();
				param =list.get(i);
				if(param.getDataType()=="String"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setString(j++,param.getValue());
					
				}else if(param.getDataType()=="int"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setInt(j++,Integer.parseInt(param.getValue()));
				
				}else if(param.getDataType()=="long"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setLong(j++,Long.parseLong(param.getValue()));
					
				}else if(param.getDataType()=="float"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setFloat(j++,Float.parseFloat(param.getValue()));
				
				}else if(param.getDataType()=="double"){
					if(showParameter)
						System.out.println(j + " : " + param.getValue());
					pstmt.setDouble(j++,Double.parseDouble(param.getValue()));
				
				}else if(param.getDataType()=="time"){	
					 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    			    java.util.Date parsedDate =  dateFormat.parse(param.getValue());
    			    java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
    			    if(showParameter)
						System.out.println(j + " : " + timestamp);
					pstmt.setTimestamp(j++,timestamp);
				}else{
					System.out.println("Error Parsing Parameter");
				}
			}
			rs = pstmt.executeQuery();
		}
		catch(Exception ex){
			System.out.println("Error In Query :" + ex.getMessage());
			System.out.println(FullQuery);
		}
		rs.next();
		return rs;
	}
	
	
	
	
	
	public long getNextSequence(String sequenceName) throws SQLException {
		String query = "select " + sequenceName + ".nextval  as SEQVAL from dual";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long seqnextVal = 0;
		try {
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				seqnextVal = rs.getLong("SEQVAL");
			}
		} catch (SQLException e) {
			System.out.println("Error Getting Sequence");
		} finally {
			rs.close();
			pstmt.close();
		}
		return seqnextVal;
	}
	
	
	
	public String getQuery(){
		System.out.println(FullQuery);
		return FullQuery;
	}
	
	public String toString(){
		return FullQuery;
	}
	
	
	
	
	
	public int close() {

		try { rs.close(); } catch (Exception e) { /* ignored */ }
	    try { pstmt.close(); } catch (Exception e) { /* ignored */ }
	    try { con.close(); } catch (Exception e) { /* ignored */ }	    
	    try { DBUtil.close(con); } catch (Exception e) { /* ignored */ }	    
		return 1;
	}

	public List<Parameter> getList() {
		return list;
	}

	public void setList(List<Parameter> list) {
		this.list = list;
	}
	
	public void refresh(){
		FullQuery = "";
		orwhere = "";
		where = "";
		coloms = "";
		from = "";
		insert = "";
		update = "";
		groupby = "";
		orderby = "";
		list.clear();
		rs = null;
		pstmt = null;
	
		
	}
	
	void setParam(String dataType,String value){
		param = new Parameter();
		param.setDataType(dataType);
		param.setValue(value);
		list.add(param);
	}
	
	
	public void rollBack(){
		try {
            if (null != con && !con.isClosed()) {
                con.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
	}


	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getInsert() {
		return insert;
	}

	public void setInsert(String insert) {
		this.insert = insert;
	}
	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public DBQuery endNested() {
		// TODO Auto-generated method stub
		where = "";
		from = "";
		return this;
	}
	public DBQuery endNested(String As) {
		// TODO Auto-generated method stub
		where = "";
		from = "";
		FullQuery = FullQuery + ") AS " + As + " ";
		return this;
	}

	

	
}


