package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pojo.StocksPojo;

public class StockData {
	String INSERT="INSERT INTO purchase_history VALUES(?,?,?)";
	String SELECT="SELECT * FROM stop_limit WHERE symbol=? ";
	String UPDATE="UPDATE stop_limit SET tradedQty=? WHERE symbol=?";
	int qunat,updatedqty;
	public String buying(StocksPojo S,Connection conn) throws SQLException {
		if(S.getSymbol().equals("AAPL") ) {
			if( S.getPrice()>=200 && S.getPrice()<=215 && S.getQuantity()<=50) {
				
			PreparedStatement pstmt=conn.prepareStatement(INSERT);
			PreparedStatement pstmt3=conn.prepareStatement(SELECT);
			PreparedStatement pstmt2=conn.prepareStatement(UPDATE);
			pstmt3.setString(1,S.getSymbol());
			ResultSet rs=pstmt3.executeQuery();
			while(rs.next()) {
				qunat=rs.getInt("tradedQty");
			}
			updatedqty=qunat+S.getQuantity();
			if(updatedqty>50) {
				return "stocks are filled";
			}
			else {
			pstmt2.setInt(1, updatedqty);
			pstmt2.setString(2,S.getSymbol());
			pstmt2.execute();
			pstmt.setString(1, S.getSymbol());
			pstmt.setInt(2,S.getPrice());
			pstmt.setInt(3,S.getQuantity());
			pstmt.execute();
			return "sucessfully traded";
		}
		}else {
			return "enter correct symbol";
		}
		
	}
		else {
			return "credentials miss match";
		}
}
}