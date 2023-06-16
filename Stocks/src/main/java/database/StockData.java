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
	int TradedQuantity, updatedqty, TotalQty, updatedqty1 = 0;
	String symbol;

	public String firstTranscationCheck(StocksPojo S, Connection conn) {
		String SELECTP = "SELECT * FROM purchase_history WHERE symbol=? ";
		PreparedStatement InsertQuery;
		try {
			InsertQuery = conn.prepareStatement(SELECTP);
			InsertQuery.setString(1, S.getSymbol());
			ResultSet rs = InsertQuery.executeQuery();
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

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "First Transcation Has Not Satarted";

	}

	public String buyingFirst(StocksPojo S, Connection conn) {

		PreparedStatement InsertQuery = null;
		PreparedStatement UpdateQuery = null;
		PreparedStatement SelectQuery = null;

		if (S.getSymbol().equals("AAPL")) {
			if (S.getPrice() == 200 && S.getQuantity() < 50) {

				try {
					InsertQuery = conn.prepareStatement(INSERT);
					SelectQuery = conn.prepareStatement(SELECT);
					UpdateQuery = conn.prepareStatement(UPDATE);
					SelectQuery.setString(1, S.getSymbol());
					ResultSet rs = SelectQuery.executeQuery();
					while (rs.next()) {
						TradedQuantity = rs.getInt("tradedQty");
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {

						if (SelectQuery != null) {
							SelectQuery.close();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				updatedqty1 = TradedQuantity + S.getQuantity();
				if (updatedqty1 > 50) {
					return "stocks are filled";
				} else {
					try {
						UpdateQuery.setInt(1, updatedqty1);
						UpdateQuery.setString(2, S.getSymbol());
						UpdateQuery.execute();
						InsertQuery.setString(1, S.getSymbol());
						InsertQuery.setInt(2, S.getPrice());
						InsertQuery.setInt(3, S.getQuantity());
						InsertQuery.execute();
						return "sucessfully traded";

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							if (UpdateQuery != null) {
								UpdateQuery.close();

							}
							if (InsertQuery != null) {
								InsertQuery.close();
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			} else if (S.getPrice() != 200) {
				return "enter correct Amount";
			}

			else {

				try {
					update(S, conn);

					InsertQuery = conn.prepareStatement(INSERT);

					InsertQuery.setString(1, S.getSymbol());
					InsertQuery.setInt(2, S.getPrice());
					InsertQuery.setInt(3, 50);
					InsertQuery.execute();
					return "Trading Triggered and and slots are filled";
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {

						if (InsertQuery != null) {
							InsertQuery.close();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		} else {
			return "Enter correct Symbol";
		}
		return "Method not started";

	}

	public void update(StocksPojo S, Connection conn) {
		PreparedStatement UpdateQuery = null;
		try {
			UpdateQuery = conn.prepareStatement(UPDATE);

			UpdateQuery.setInt(1, 50);
			UpdateQuery.setString(2, S.getSymbol());
			UpdateQuery.execute();
			history(S, conn);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (UpdateQuery != null) {
					UpdateQuery.close();

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void history(StocksPojo S, Connection conn) {
		PreparedStatement InsertQuery = null;
		try {
			InsertQuery = conn.prepareStatement(INSERT);
			InsertQuery.setString(1, S.getSymbol());
			InsertQuery.setInt(2, S.getPrice());
			InsertQuery.setInt(3, S.getQuantity());
			InsertQuery.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (InsertQuery != null) {
					InsertQuery.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String buying(StocksPojo S, Connection conn) {

		PreparedStatement InsertQuery = null;
		PreparedStatement UpdateQuery = null;
		PreparedStatement SelectQuery = null;

		int q = 0;

		if (S.getSymbol().equals("AAPL") && S.getPrice() >= 200 && S.getPrice() < 216) {
			if ((S.getQuantity() > 0)) {
				try {
					SelectQuery = conn.prepareStatement(SELECT);

					UpdateQuery = conn.prepareStatement(UPDATE);
					SelectQuery.setString(1, S.getSymbol());
					ResultSet rs = SelectQuery.executeQuery();
					while (rs.next()) {
						TradedQuantity = rs.getInt("tradedQty");
						TotalQty = rs.getInt("quantity");
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				q = TradedQuantity + S.getQuantity();
				System.out.println(q);
				if (q >= 50 && TradedQuantity < 50) {
					try {
						UpdateQuery.setInt(1, 50);
						UpdateQuery.setString(2, S.getSymbol());
						UpdateQuery.execute();
						InsertQuery = conn.prepareStatement(INSERT);

						InsertQuery.setString(1, S.getSymbol());
						InsertQuery.setInt(2, S.getPrice());
						InsertQuery.setInt(3, 50 - TradedQuantity);
						InsertQuery.execute();
						return "Trading Triggered and and slots are filled";

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							if (UpdateQuery != null) {
								UpdateQuery.close();

							}
							if (InsertQuery != null) {
								InsertQuery.close();
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else if (q <= 50 && TradedQuantity < 50) {
					try {
						UpdateQuery.setInt(1, q);
						UpdateQuery.setString(2, S.getSymbol());
						UpdateQuery.execute();
						history(S, conn);
						return "sucessfully traded";

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							if (UpdateQuery != null) {
								UpdateQuery.close();

							}

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			} else {
				return "Trading not triggered";
			}
		} else {
			return "Enter amount between 200 and 215";
		}
		return "Trading not triggered slots are filled";

	}
}