import java.sql.*;
import java.util.ArrayList;
		public class PaymentManagement {
			
			public static ArrayList<Integer> orderNumberResult = new ArrayList<Integer>();
			public static ArrayList<String> checkNumberResult = new ArrayList<String>();
			
			public static void reconcilePayments(Connection database) {
				try{
					Statement stmt=database.createStatement();
					stmt.execute("use htrivedi;");
					ResultSet result1=stmt.executeQuery("ALTER TABLE orders ADD paymentStatus varchar(255);");
					ResultSet result2=stmt.executeQuery("ALTER TABLE orders ADD amount float4;");
					ResultSet result3=stmt.executeQuery("ALTER TABLE orders ADD checkNumber varchar(255);");
					ResultSet result4 = stmt.executeQuery("UPDATE orders o join payments p on p.customerNumber=o.customerNumber set o.checkNumber = p.checkNumber;");
					ResultSet result5 = stmt.executeQuery("UPDATE orders o join (select sum(orderdetails.quantityOrdered*orderdetails.priceEach) as sales, orderNumber from orderdetails group by orderNumber) result on  o.orderNumber = result.orderNumber set o.amount = result.sales;");
					ResultSet result6 = stmt.executeQuery("UPDATE orders o join payments p set o.paymentStatus = 'YES' where (o.amount) = (p.amount) and o.checkNumber = p.checkNumber and o.paymentStatus is null;");
					result1.close();
					result2.close();
					result3.close();
					result4.close();
					result5.close();
					result6.close();
					stmt.close();
				}
				catch(Exception e) {
					System.out.println(e);
				}
				
			}
			
			public static ArrayList<Integer> unpaidOrders(Connection database) {
				try {Statement stmt=database.createStatement();
				stmt.execute("use htrivedi;");
						ResultSet rs = stmt.executeQuery("select orderNumber from orders where paymentStatus is not null and status not like 'Cancelled' and status not like 'Disputed';"); 
						while(rs.next()) {
							orderNumberResult.add(new Integer(rs.getString("orderNumber")));
						}
						stmt.close();
						rs.close();
						return orderNumberResult;
				}
				catch(Exception e) {
					System.out.println(e);
				}
				return null;
				
			}
			
			
			public static ArrayList<String> unknownPayments(Connection database){
				try {
					Statement stmt=database.createStatement();
					stmt.execute("use htrivedi;");
						ResultSet rs = stmt.executeQuery("SELECT checkNumber FROM payments except select checkNumber from orders;"); 
						while(rs.next()) {
							checkNumberResult.add(rs.getString("checkNumber"));
						}
						stmt.close();
						rs.close();
						return checkNumberResult;
				}
				catch(Exception e) {
					System.out.println(e);
				}
				return null;	
				}
			
			boolean payOrder() {
				return false;
			}

		public static void main(String[] args) {
			try{ 
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection database=DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC", "htrivedi", "B00836700");
				reconcilePayments(database);
				unknownPayments(database);
				unpaidOrders(database);																																																																																																																																																																																																																																																																																																																																																																																										
				database.close(); 
				
			}
			catch(Exception e)
			{
				System.out.println(e);
			} 
		} 
	}