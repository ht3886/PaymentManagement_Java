
Overview
--------

This program is used to generate a connection between orders and payments 
table in the sales database. I have manipulated my own copy of database to
achieve this solution. A summary of the formatting requirements is in the CSCI 3901 
course assignment #6 information in the course's Brightspace space.

In order to know which invoices have been paid and which are not:
  - create a new column paymentStatus in the payments table.
  - copy checkNumber field in orders table
  - determine the total sum of each order and map with corresponding customer number.
  - finally map payments table and orders table
  - determine if payment corresponding to respective invoice is pending or completed.

Files and external data
-----------------------

There are two main files:
  - PaymentManagement.java -- class file that relates orders and payments table
  - MyQueries.txt -- text file that lists the SQL statements which i used to modify the database schema.

Data structures and their relations to each other
-------------------------------------------------

The program encodes the results in set of arrayLists.

  orders -- Records the receipt of a payment, with the given cheque number, that is
	supposed to cover all of the listed orders. Return true if the payments were
	recorded as proper payments and false if the payments aren’t recorded.
  unpaidOrders -- Return a list of all the orders for which we have no record of a 
	             payment in the database. Exclude cancelled and disputed orders.
  unknownPayments -- Return a list of all the cheque numbers that we haven’t 
		     managed to pair up with orders in the database.
Assumptions
-----------

  - No past order was partially paid and no past order was paid in installments.
  - Orders are paid in full or not at all.
  - Overpayments for orders are not allowed.
  - Customers have only paid for their own orders.
  - Repay of order is invalid.

Choices
-------

  - The orders that are cancelled or disrupted are not considered.

Key algorithms and design elements
----------------------------------

The program processes the queries on the database named "htrivedi"
which is a duplicate of sales database. I have duplicated the checkNumber
field from payments table and added it to orders table.

Next, using the total sum of each order that is corresponding to respective
customer, I have mapped the payments table with orders table.

The program then determines if payments recorded were proper or
improper by using the payOrder method. It requires the value of 
total amount, cheque_number in doing so.

Similarly, the orders for which payments are pending and the cheque
numbers that do not pair up with orders are determined

Limitations
-----------

The above approach is what i planned to implement, but somehow 
was able to achieve it partially.

