package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pojo.StocksPojo;

public class StockData {
	String INSERT = "INSERT INTO purchase_history VALUES(?,?,?)";
	String SELECT = "SELECT * FROM stop_limit WHERE symbol=? ";
	String UPDATE = "UPDATE stop_limit SET tradedQty=? WHERE symbol=?";
	int qunat, updatedqty, TotalQty, updatedqty1 = 0;
	String symbol;

	public String firstTranscationCheck(StocksPojo S, Connection conn) throws SQLException {
		String SELECTP = "SELECT * FROM purchase_history WHERE symbol=? ";
		PreparedStatement pstmt;
		pstmt = conn.prepareStatement(SELECTP);
		pstmt.setString(1, S.getSymbol());
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			symbol = rs.getString("symbol");
		}
		if (symbol == null) {
			String msg = buyingFirst(S, conn);

			return msg;
		} else {
			String msg = buying(S, conn);
			return msg;
		}

	}

	public String buyingFirst(StocksPojo S, Connection conn) throws SQLException {
		if (S.getSymbol().equals("AAPL")) {
			if (S.getPrice() == 200 && S.getQuantity() < 50) {

				PreparedStatement pstmt = conn.prepareStatement(INSERT);
				PreparedStatement pstmt3 = conn.prepareStatement(SELECT);
				PreparedStatement pstmt2 = conn.prepareStatement(UPDATE);
				pstmt3.setString(1, S.getSymbol());
				ResultSet rs = pstmt3.executeQuery();
				while (rs.next()) {
					qunat = rs.getInt("tradedQty");
				}
				updatedqty1 = qunat + S.getQuantity();
				if (updatedqty1 > 50) {
					return "stocks are filled";
				} else {
					pstmt2.setInt(1, updatedqty1);
					pstmt2.setString(2, S.getSymbol());
					pstmt2.execute();
					pstmt.setString(1, S.getSymbol());
					pstmt.setInt(2, S.getPrice());
					pstmt.setInt(3, S.getQuantity());
					pstmt.execute();
					return "sucessfully traded";
				}
			} else if (S.getPrice() != 200) {
				return "enter correct Amount";
			}

			else {
//				int s=S.getQuantity()-qunat;

				update(S, conn);
				PreparedStatement pstmt = conn.prepareStatement(INSERT);

				pstmt.setString(1, S.getSymbol());
				pstmt.setInt(2, S.getPrice());
				pstmt.setInt(3, 50);
				pstmt.execute();		
				return "Trading Triggered and and slots are filled";
			}

		} else {
			return "Enter correct symbol";
		}

	}

	public void update(StocksPojo S, Connection conn) {
		PreparedStatement pstmt2;
		try {
			pstmt2 = conn.prepareStatement(UPDATE);

			pstmt2.setInt(1, 50);
			pstmt2.setString(2, S.getSymbol());
			pstmt2.execute();
			history(S, conn);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void history(StocksPojo S, Connection conn) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, S.getSymbol());
			pstmt.setInt(2, S.getPrice());
			pstmt.setInt(3, S.getQuantity());
			pstmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String buying(StocksPojo S, Connection conn) throws SQLException {
		if (S.getSymbol().equals("AAPL")) {
			if ((S.getQuantity() <= 50)) {

				PreparedStatement pstmt3 = conn.prepareStatement(SELECT);
				PreparedStatement pstmt2 = conn.prepareStatement(UPDATE);
				pstmt3.setString(1, S.getSymbol());
				ResultSet rs = pstmt3.executeQuery();
				while (rs.next()) {
					qunat = rs.getInt("tradedQty");
					TotalQty = rs.getInt("quantity");
				}

				int q = qunat + S.getQuantity();

				if (q > 50) {

					pstmt2.setInt(1, 50);
					pstmt2.setString(2, S.getSymbol());
					pstmt2.execute();
					PreparedStatement pstmt = conn.prepareStatement(INSERT);

					pstmt.setString(1, S.getSymbol());
					pstmt.setInt(2, S.getPrice());
					pstmt.setInt(3, 50-qunat);
					pstmt.execute();		

					return "Trading Triggered and and slots are filled";

				} else if (q < 50) {

					pstmt2.setInt(1, q);
					pstmt2.setString(2, S.getSymbol());
					pstmt2.execute();
					history(S, conn);
					return "Trading triggered";

				}

			} else {
				return "Trading not triggered";
			}
		} else {
			return "enter correct Amount";
		}
		return null;

//		} else {
//			return "enter correct symbol";
//		}
	}
}