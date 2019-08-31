package com.example.studentmanagement.model;

public class Book {

    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private int bookPrice;
    private String bookReck;
    private String bookDes;
    private String isbn_no;
    private String subject_name;
    private String book_no;
    private int id;
    private int category_id;
    private int quantity;
    private double active_status;

    public Book(String bookTitle, String bookAuthor, String bookPublisher, int bookPrice, String bookReck,
                String bookDes, String isbn_no, int id, int category_id, int quantity, double active_status,String subject_name,String book_no) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookPrice = bookPrice;
        this.bookReck = bookReck;
        this.bookDes = bookDes;
        this.isbn_no = isbn_no;
        this.id = id;
        this.category_id = category_id;
        this.quantity = quantity;
        this.active_status = active_status;
        this.subject_name =subject_name;
        this.book_no = book_no;
    }

    public Book(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public boolean checkAndSet(String str , int count){

        String sub = this.getBookTitle().substring(0,count);

        if(sub.equalsIgnoreCase(str)){
            return true;
        }else{
            return false;
        }

    }

//    @Override
//    public boolean equals(Object obj) {
//
//        if (obj == null) {
//            return false;
//        }
//
//        if (!(obj instanceof Book)) {
//            return false;
//        }
//
//       Book book = (Book) obj;
//
//        return this.bookTitle.contains(book.bookTitle);
//
//    }


    public String getBook_no() {
        return book_no;
    }

    public void setBook_no(String book_no) {
        this.book_no = book_no;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(int bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookReck() {
        return bookReck;
    }

    public void setBookReck(String bookReck) {
        this.bookReck = bookReck;
    }

    public String getBookDes() {
        return bookDes;
    }

    public void setBookDes(String bookDes) {
        this.bookDes = bookDes;
    }

    public String getIsbn_no() {
        return isbn_no;
    }

    public void setIsbn_no(String isbn_no) {
        this.isbn_no = isbn_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getActive_status() {
        return active_status;
    }

    public void setActive_status(double active_status) {
        this.active_status = active_status;
    }
}
