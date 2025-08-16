//package com.lms;
//
//import com.lms.domain.*;
//import com.lms.services.*;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Scanner;
//
//@SpringBootApplication
//public class LibraryManagementSystemApplication implements CommandLineRunner {
//
//	@Autowired
//	private StaffService staffService;
//
//	@Autowired
//	private BookService bookService;
//
//	@Autowired
//	private MemberService memberService;
//
//	@Autowired
//	private BookLoanService bookLoanService;
//
//	@Autowired
//	private AuthorService authorService;
//
//	@Autowired
//	private CategoryService categoryService;
//
//	public static void main(String[] args) {
//		SpringApplication.run(LibraryManagementSystemApplication.class, args);
//	}
//
//	@Override
//	public void run(String... args) {
//		Scanner scanner = new Scanner(System.in);
//
//		System.out.println("=== Library Management System ===");
//
//		System.out.print("Enter Username: ");
//		String username = scanner.nextLine();
//
//		System.out.print("Enter Password: ");
//		String password = scanner.nextLine();
//
//		Optional<Staff> staffOpt = Optional.ofNullable(staffService.getStaffByUsername(username));
//
//		if (staffOpt.isPresent()) {
//			Staff staff = staffOpt.get();
//			if (staff.getPassword().equals(password)) {
//				System.out.println("✅ Login Successful! Welcome, " + staff.getName());
//				showMenu(scanner);
//			} else {
//				System.out.println("❌ Login Failed: Incorrect password.");
//			}
//		} else {
//			System.out.println("❌ Login Failed: User not found.");
//		}
//	}
//
//	private void showMenu(Scanner scanner) {
//		while (true) {
//			System.out.println("\n=== Main Menu ===");
//			System.out.println("1. View All Books");
//			System.out.println("2. Add Book");
//			System.out.println("3. Issue Book");
//			System.out.println("4. Return Book");
//			System.out.println("5. View All Members");
//			System.out.println("6. Add Member");
//			System.out.println("7. View All Authors");
//			System.out.println("8. Add Author");
//			System.out.println("9. View All Categories");
//			System.out.println("10. Add Category");
//			System.out.println("11. Exit");
//			System.out.print("Choose an option: ");
//
//			int choice = Integer.parseInt(scanner.nextLine());
//
//			switch (choice) {
//				case 1:
//					List<Book> books = bookService.getAllBooks();
//					if (books.isEmpty()) {
//						System.out.println("No books found.");
//					} else {
//						books.forEach(book ->
//								System.out.println(book.getId() + " - " + book.getTitle() +
//										" (Available: " + book.getAvailableCopies() + ")"));
//					}
//					break;
//
//				case 2:
//					System.out.print("Enter ISBN: ");
//					String isbn = scanner.nextLine();
//					System.out.print("Enter Title: ");
//					String title = scanner.nextLine();
//					System.out.print("Enter Description: ");
//					String description = scanner.nextLine();
//					System.out.print("Enter Publication Year: ");
//					int year = Integer.parseInt(scanner.nextLine());
//					System.out.print("Enter Total Copies: ");
//					int totalCopies = Integer.parseInt(scanner.nextLine());
//
//					Book newBook = Book.builder()
//							.isbn(isbn)
//							.title(title)
//							.description(description)
//							.publicationYear(year)
//							.totalCopies(totalCopies)
//							.availableCopies(totalCopies)
//							.build();
//
//					bookService.addBook(newBook);
//					System.out.println("✅ Book added successfully!");
//					break;
//
//				case 3:
//					System.out.print("Enter Book ID: ");
//					Long bookId = Long.parseLong(scanner.nextLine());
//					System.out.print("Enter Member ID: ");
//					Long memberId = Long.parseLong(scanner.nextLine());
//					try {
//						BookLoan loan = bookLoanService.issueBook(bookId, memberId);
//						System.out.println("✅ Book issued successfully! Loan ID: " + loan.getId());
//					} catch (Exception e) {
//						System.out.println("❌ Error: " + e.getMessage());
//					}
//					break;
//
//				case 4:
//					System.out.print("Enter Loan ID: ");
//					Long loanId = Long.parseLong(scanner.nextLine());
//					try {
//						bookLoanService.returnBook(loanId);
//						System.out.println("✅ Book returned successfully!");
//					} catch (Exception e) {
//						System.out.println("❌ Error: " + e.getMessage());
//					}
//					break;
//
//				case 5:
//					List<Member> members = memberService.getAllMembers();
//					if (members.isEmpty()) {
//						System.out.println("No members found.");
//					} else {
//						members.forEach(m -> System.out.println(m.getId() + " - " + m.getName()));
//					}
//					break;
//
//				case 6:
//					System.out.print("Enter Member Name: ");
//					String memberName = scanner.nextLine();
//					System.out.print("Enter Email: ");
//					String email = scanner.nextLine();
//					System.out.print("Enter Phone: ");
//					String phone = scanner.nextLine();
//					System.out.print("Enter Address: ");
//					String address = scanner.nextLine();
//
//					Member member = Member.builder()
//							.name(memberName)
//							.email(email)
//							.phoneNumber(phone)
//							.address(address)
//							.build();
//
//					memberService.registerMember(member);
//					System.out.println("✅ Member added successfully!");
//					break;
//
//				case 7:
//					List<Author> authors = authorService.getAllAuthors();
//					if (authors.isEmpty()) {
//						System.out.println("No authors found.");
//					} else {
//						authors.forEach(a -> System.out.println(a.getId() + " - " + a.getName()));
//					}
//					break;
//
//				case 8:
//					System.out.print("Enter Author Name: ");
//					String authorName = scanner.nextLine();
//					System.out.print("Enter Biography: ");
//					String bio = scanner.nextLine();
//
//					Author author = new Author();
//					author.setName(authorName);
//					author.setBiography(bio);
//
//					authorService.addAuthor(author);
//					System.out.println("✅ Author added successfully!");
//					break;
//
//				case 9:
//					List<Category> categories = categoryService.getAllCategories();
//					if (categories.isEmpty()) {
//						System.out.println("No categories found.");
//					} else {
//						categories.forEach(c -> System.out.println(c.getId() + " - " + c.getName()));
//					}
//					break;
//
//				case 10:
//					System.out.print("Enter Category Name: ");
//					String categoryName = scanner.nextLine();
//					System.out.print("Enter Description: ");
//					String categoryDesc = scanner.nextLine();
//
//					Category category = Category.builder()
//							.name(categoryName)
//							.description(categoryDesc)
//							.build();
//
//					categoryService.addCategory(category);
//					System.out.println("✅ Category added successfully!");
//					break;
//
//				case 11:
//					System.out.println("👋 Exiting system. Goodbye!");
//					return;
//
//				default:
//					System.out.println("❌ Invalid choice. Please try again.");
//			}
//		}
//	}
//}



//
//package com.lms;
//
//import com.lms.domain.*;
//import com.lms.services.*;
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import javafx.stage.Stage;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.ConfigurableApplicationContext;
//
//import java.util.List;
//import java.util.Optional;
//
//@SpringBootApplication
//public class LibraryManagementSystemApplication extends Application {
//
//	private ConfigurableApplicationContext context;
//
//	private StaffService staffService;
//	private BookService bookService;
//	private MemberService memberService;
//	private BookLoanService bookLoanService;
//	private AuthorService authorService;
//	private CategoryService categoryService;
//
//	public static void main(String[] args) {
//		launch(args); // JavaFX entry point
//	}
//
//	@Override
//	public void init() {
//		// Start Spring Boot inside JavaFX
//		context = new SpringApplicationBuilder(LibraryManagementSystemApplication.class).run();
//		staffService = context.getBean(StaffService.class);
//		bookService = context.getBean(BookService.class);
//		memberService = context.getBean(MemberService.class);
//		bookLoanService = context.getBean(BookLoanService.class);
//		authorService = context.getBean(AuthorService.class);
//		categoryService = context.getBean(CategoryService.class);
//	}
//
//	@Override
//	public void start(Stage stage) {
//		showLogin(stage);
//	}
//
//	private void showLogin(Stage stage) {
//		Label title = new Label("📚 Library Management System");
//		title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
//
//		TextField usernameField = new TextField();
//		usernameField.setPromptText("Username");
//
//		PasswordField passwordField = new PasswordField();
//		passwordField.setPromptText("Password");
//
//		Label message = new Label();
//
//		Button loginBtn = new Button("Login");
//		loginBtn.setOnAction(e -> {
//			String username = usernameField.getText();
//			String password = passwordField.getText();
//
//			Optional<Staff> staffOpt = Optional.ofNullable(staffService.getStaffByUsername(username));
//			if (staffOpt.isPresent() && staffOpt.get().getPassword().equals(password)) {
//				message.setText("✅ Welcome " + staffOpt.get().getName());
//				showMainMenu(stage);
//			} else {
//				message.setText("❌ Invalid username or password!");
//			}
//		});
//
//		VBox loginLayout = new VBox(10, title, usernameField, passwordField, loginBtn, message);
//		loginLayout.setStyle("-fx-padding: 20; -fx-alignment: center;");
//
//		stage.setScene(new Scene(loginLayout, 400, 300));
//		stage.setTitle("Library Login");
//		stage.show();
//	}
//
//	private void showMainMenu(Stage stage) {
//		Label title = new Label("=== Main Menu ===");
//		title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
//
//		Button viewBooksBtn = new Button("View All Books");
//		viewBooksBtn.setOnAction(e -> showBooks(stage));
//
//		Button addBookBtn = new Button("Add Book");
//		addBookBtn.setOnAction(e -> showAddBook(stage));
//
//		Button viewMembersBtn = new Button("View Members");
//		viewMembersBtn.setOnAction(e -> showMembers(stage));
//
//		Button addMemberBtn = new Button("Add Member");
//		addMemberBtn.setOnAction(e -> showAddMember(stage));
//
//		Button exitBtn = new Button("Exit");
//		exitBtn.setOnAction(e -> stage.close());
//
//		VBox menuLayout = new VBox(10, title, viewBooksBtn, addBookBtn, viewMembersBtn, addMemberBtn, exitBtn);
//		menuLayout.setStyle("-fx-padding: 20; -fx-alignment: center;");
//
//		stage.setScene(new Scene(menuLayout, 400, 400));
//		stage.setTitle("Library Main Menu");
//	}
//
//	private void showBooks(Stage stage) {
//		List<Book> books = bookService.getAllBooks();
//		ListView<String> listView = new ListView<>();
//		if (books.isEmpty()) {
//			listView.setItems(FXCollections.observableArrayList("No books found."));
//		} else {
//			listView.setItems(FXCollections.observableArrayList(
//					books.stream().map(b -> b.getId() + " - " + b.getTitle() +
//							" (Available: " + b.getAvailableCopies() + ")").toList()
//			));
//		}
//
//		Button backBtn = new Button("Back");
//		backBtn.setOnAction(e -> showMainMenu(stage));
//
//		VBox layout = new VBox(10, new Label("📖 Books"), listView, backBtn);
//		layout.setStyle("-fx-padding: 20;");
//
//		stage.setScene(new Scene(layout, 500, 400));
//	}
//
//	private void showAddBook(Stage stage) {
//		TextField isbnField = new TextField();
//		isbnField.setPromptText("ISBN");
//		TextField titleField = new TextField();
//		titleField.setPromptText("Title");
//		TextField descField = new TextField();
//		descField.setPromptText("Description");
//		TextField yearField = new TextField();
//		yearField.setPromptText("Year");
//		TextField copiesField = new TextField();
//		copiesField.setPromptText("Total Copies");
//
//		Label msg = new Label();
//
//		Button saveBtn = new Button("Save");
//		saveBtn.setOnAction(e -> {
//			try {
//				Book newBook = Book.builder()
//						.isbn(isbnField.getText())
//						.title(titleField.getText())
//						.description(descField.getText())
//						.publicationYear(Integer.parseInt(yearField.getText()))
//						.totalCopies(Integer.parseInt(copiesField.getText()))
//						.availableCopies(Integer.parseInt(copiesField.getText()))
//						.build();
//				bookService.addBook(newBook);
//				msg.setText("✅ Book added successfully!");
//			} catch (Exception ex) {
//				msg.setText("❌ Error: " + ex.getMessage());
//			}
//		});
//
//		Button backBtn = new Button("Back");
//		backBtn.setOnAction(e -> showMainMenu(stage));
//
//		VBox layout = new VBox(10, new Label("➕ Add Book"), isbnField, titleField, descField, yearField, copiesField, saveBtn, msg, backBtn);
//		layout.setStyle("-fx-padding: 20;");
//
//		stage.setScene(new Scene(layout, 400, 400));
//	}
//
//	private void showMembers(Stage stage) {
//		List<Member> members = memberService.getAllMembers();
//		ListView<String> listView = new ListView<>();
//		if (members.isEmpty()) {
//			listView.setItems(FXCollections.observableArrayList("No members found."));
//		} else {
//			listView.setItems(FXCollections.observableArrayList(
//					members.stream().map(m -> m.getId() + " - " + m.getName()).toList()
//			));
//		}
//
//		Button backBtn = new Button("Back");
//		backBtn.setOnAction(e -> showMainMenu(stage));
//
//		VBox layout = new VBox(10, new Label("👥 Members"), listView, backBtn);
//		layout.setStyle("-fx-padding: 20;");
//
//		stage.setScene(new Scene(layout, 500, 400));
//	}
//
//	private void showAddMember(Stage stage) {
//		TextField idField = new TextField();
//		idField.setPromptText("Member ID");
//
//		TextField nameField = new TextField();
//		nameField.setPromptText("Name");
//
//		TextField emailField = new TextField();
//		emailField.setPromptText("Email");
//
//		TextField phoneField = new TextField();
//		phoneField.setPromptText("Phone");
//
//		TextField addressField = new TextField();
//		addressField.setPromptText("Address");
//
//		Label msg = new Label();
//
//		Button saveBtn = new Button("Save");
//		saveBtn.setOnAction(e -> {
//			try {
//				Member member = Member.builder()
//						.memberId(idField.getText())  // ✅ Now we set Member ID
//						.name(nameField.getText())
//						.email(emailField.getText())
//						.phoneNumber(phoneField.getText())
//						.address(addressField.getText())
//						.build();
//
//				memberService.registerMember(member);
//				msg.setText("✅ Member added successfully!");
//			} catch (Exception ex) {
//				msg.setText("❌ Error: " + ex.getMessage());
//			}
//		});
//
//		Button backBtn = new Button("Back");
//		backBtn.setOnAction(e -> showMainMenu(stage));
//
//		VBox layout = new VBox(
//				10,
//				new Label("➕ Add Member"),
//				idField,
//				nameField,
//				emailField,
//				phoneField,
//				addressField,
//				saveBtn,
//				msg,
//				backBtn
//		);
//		layout.setStyle("-fx-padding: 20;");
//
//		stage.setScene(new Scene(layout, 400, 400));
//	}
//
//
//	@Override
//	public void stop() {
//		context.close();
//	}
//}



//package com.lms;
//
//import com.lms.domain.*;
//import com.lms.services.*;
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.ConfigurableApplicationContext;
//
//import java.util.List;
//import java.util.Optional;
//
//@SpringBootApplication
//public class LibraryManagementSystemApplication extends Application {
//
//	private ConfigurableApplicationContext context;
//
//	private StaffService staffService;
//	private BookService bookService;
//	private MemberService memberService;
//	private BookLoanService bookLoanService;
//	private AuthorService authorService;
//	private CategoryService categoryService;
//
//	public static void main(String[] args) {
//		launch(args);
//	}
//
//	@Override
//	public void init() {
//		context = new SpringApplicationBuilder(LibraryManagementSystemApplication.class).run();
//		staffService = context.getBean(StaffService.class);
//		bookService = context.getBean(BookService.class);
//		memberService = context.getBean(MemberService.class);
//		bookLoanService = context.getBean(BookLoanService.class);
//		authorService = context.getBean(AuthorService.class);
//		categoryService = context.getBean(CategoryService.class);
//	}
//
//	@Override
//	public void start(Stage stage) {
//		stage.setMaximized(true);
//		showLogin(stage);
//	}
//
//	private void showLogin(Stage stage) {
//		Label title = new Label("📚 Library Management System");
//		title.setFont(Font.font("Roboto", 28));
//		title.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");
//
//		TextField usernameField = new TextField();
//		usernameField.setPromptText("Username");
//		usernameField.setFont(Font.font("Roboto", 16));
//		usernameField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5; -fx-padding: 10;");
//
//		PasswordField passwordField = new PasswordField();
//		passwordField.setPromptText("Password");
//		passwordField.setFont(Font.font("Roboto", 16));
//		passwordField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5; -fx-padding: 10;");
//
//		Label message = new Label();
//		message.setFont(Font.font("Roboto", 14));
//		message.setStyle("-fx-text-fill: #e74c3c;");
//
//		Button loginBtn = new Button("Login");
//		loginBtn.setFont(Font.font("Roboto", 16));
//		loginBtn.setStyle(
//				"-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;"
//		);
//		loginBtn.setOnMouseEntered(e -> loginBtn.setStyle(
//				"-fx-background-color: #2980b9; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;"
//		));
//		loginBtn.setOnMouseExited(e -> loginBtn.setStyle(
//				"-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;"
//		));
//		loginBtn.setOnAction(e -> {
//			String username = usernameField.getText();
//			String password = passwordField.getText();
//
//			Optional<Staff> staffOpt = Optional.ofNullable(staffService.getStaffByUsername(username));
//			if (staffOpt.isPresent() && staffOpt.get().getPassword().equals(password)) {
//				message.setText("✅ Welcome " + staffOpt.get().getName());
//				message.setStyle("-fx-text-fill: #2ecc71;");
//				showMainMenu(stage);
//			} else {
//				message.setText("❌ Invalid username or password!");
//				message.setStyle("-fx-text-fill: #e74c3c;");
//			}
//		});
//
//		VBox loginLayout = new VBox(20, title, usernameField, passwordField, loginBtn, message);
//		loginLayout.setAlignment(Pos.CENTER);
//		loginLayout.setPadding(new Insets(30));
//		loginLayout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");
//
//		Scene scene = new Scene(loginLayout, 600, 500);
//		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
//		stage.setScene(scene);
//		stage.setTitle("Library Login");
//		stage.show();
//	}
//
//	private void showMainMenu(Stage stage) {
//		Label title = new Label("=== Main Menu ===");
//		title.setFont(Font.font("Roboto", 24));
//		title.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");
//
//		Button viewBooksBtn = createStyledButton("View All Books", e -> showBooks(stage));
//		Button addBookBtn = createStyledButton("Add Book", e -> showAddBook(stage));
//		Button viewMembersBtn = createStyledButton("View Members", e -> showMembers(stage));
//		Button addMemberBtn = createStyledButton("Add Member", e -> showAddMember(stage));
//		Button issueBookBtn = createStyledButton("Issue Book", e -> showIssueBook(stage));
//		Button returnBookBtn = createStyledButton("Return Book", e -> showReturnBook(stage));
//		Button viewMemberLoansBtn = createStyledButton("View Member Loans", e -> showMemberLoans(stage));
//		Button viewOverdueLoansBtn = createStyledButton("View Overdue Loans", e -> showOverdueLoans(stage));
//		Button exitBtn = createStyledButton("Exit", e -> stage.close());
//
//		VBox menuLayout = new VBox(20, title, viewBooksBtn, addBookBtn, viewMembersBtn, addMemberBtn,
//				issueBookBtn, returnBookBtn, viewMemberLoansBtn, viewOverdueLoansBtn, exitBtn);
//		menuLayout.setAlignment(Pos.CENTER);
//		menuLayout.setPadding(new Insets(30));
//		menuLayout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");
//
//		Scene scene = new Scene(menuLayout, 600, 700);
//		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
//		stage.setScene(scene);
//		stage.setTitle("Library Main Menu");
//	}
//
//	private void showBooks(Stage stage) {
//		List<Book> books = bookService.getAllBooks();
//		ListView<String> listView = new ListView<>();
//		listView.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5;");
//		if (books.isEmpty()) {
//			listView.setItems(FXCollections.observableArrayList("No books found."));
//		} else {
//			listView.setItems(FXCollections.observableArrayList(
//					books.stream().map(b -> b.getId() + " - " + b.getTitle() +
//							" (Available: " + b.getAvailableCopies() + ")").toList()
//			));
//		}
//
//		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));
//
//		Label booksLabel = new Label("📖 Books");
//		booksLabel.setFont(Font.font("Roboto", 20));
//		booksLabel.setStyle("-fx-text-fill: #2c3e50;");
//
//		VBox layout = new VBox(20, booksLabel, listView, backBtn);
//		layout.setPadding(new Insets(30));
//		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");
//
//		Scene scene = new Scene(layout, 800, 600);
//		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
//		stage.setScene(scene);
//	}
//
//	private void showAddBook(Stage stage) {
//		TextField isbnField = createStyledTextField("ISBN");
//		TextField titleField = createStyledTextField("Title");
//		TextField descField = createStyledTextField("Description");
//		TextField yearField = createStyledTextField("Year");
//		TextField copiesField = createStyledTextField("Total Copies");
//
//		Label msg = new Label();
//		msg.setFont(Font.font("Roboto", 14));
//		msg.setStyle("-fx-text-fill: #e74c3c;");
//
//		Button saveBtn = createStyledButton("Save", e -> {
//			try {
//				Book newBook = Book.builder()
//						.isbn(isbnField.getText())
//						.title(titleField.getText())
//						.description(descField.getText())
//						.publicationYear(Integer.parseInt(yearField.getText()))
//						.totalCopies(Integer.parseInt(copiesField.getText()))
//						.availableCopies(Integer.parseInt(copiesField.getText()))
//						.build();
//				bookService.addBook(newBook);
//				msg.setText("✅ Book added successfully!");
//				msg.setStyle("-fx-text-fill: #2ecc71;");
//			} catch (Exception ex) {
//				msg.setText("❌ Error: " + ex.getMessage());
//				msg.setStyle("-fx-text-fill: #e74c3c;");
//			}
//		});
//
//		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));
//
//		Label addBookLabel = new Label("➕ Add Book");
//		addBookLabel.setFont(Font.font("Roboto", 20));
//		addBookLabel.setStyle("-fx-text-fill: #2c3e50;");
//
//		VBox layout = new VBox(20, addBookLabel, isbnField, titleField, descField, yearField, copiesField, saveBtn, msg, backBtn);
//		layout.setPadding(new Insets(30));
//		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");
//
//		Scene scene = new Scene(layout, 600, 600);
//		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
//		stage.setScene(scene);
//	}
//
//	private void showMembers(Stage stage) {
//		List<Member> members = memberService.getAllMembers();
//		ListView<String> listView = new ListView<>();
//		listView.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5;");
//		if (members.isEmpty()) {
//			listView.setItems(FXCollections.observableArrayList("No members found."));
//		} else {
//			listView.setItems(FXCollections.observableArrayList(
//					members.stream().map(m -> m.getId() + " - " + m.getName()).toList()
//			));
//		}
//
//		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));
//
//		Label membersLabel = new Label("👥 Members");
//		membersLabel.setFont(Font.font("Roboto", 20));
//		membersLabel.setStyle("-fx-text-fill: #2c3e50;");
//
//		VBox layout = new VBox(20, membersLabel, listView, backBtn);
//		layout.setPadding(new Insets(30));
//		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");
//
//		Scene scene = new Scene(layout, 800, 600);
//		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
//		stage.setScene(scene);
//	}
//
//	private void showAddMember(Stage stage) {
//		TextField idField = createStyledTextField("Member ID");
//		TextField nameField = createStyledTextField("Name");
//		TextField emailField = createStyledTextField("Email");
//		TextField phoneField = createStyledTextField("Phone");
//		TextField addressField = createStyledTextField("Address");
//
//		Label msg = new Label();
//		msg.setFont(Font.font("Roboto", 14));
//		msg.setStyle("-fx-text-fill: #e74c3c;");
//
//		Button saveBtn = createStyledButton("Save", e -> {
//			try {
//				Member member = Member.builder()
//						.memberId(idField.getText())
//						.name(nameField.getText())
//						.email(emailField.getText())
//						.phoneNumber(phoneField.getText())
//						.address(addressField.getText())
//						.build();
//				memberService.registerMember(member);
//				msg.setText("✅ Member added successfully!");
//				msg.setStyle("-fx-text-fill: #2ecc71;");
//			} catch (Exception ex) {
//				msg.setText("❌ Error: " + ex.getMessage());
//				msg.setStyle("-fx-text-fill: #e74c3c;");
//			}
//		});
//
//		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));
//
//		Label addMemberLabel = new Label("➕ Add Member");
//		addMemberLabel.setFont(Font.font("Roboto", 20));
//		addMemberLabel.setStyle("-fx-text-fill: #2c3e50;");
//
//		VBox layout = new VBox(20, addMemberLabel, idField, nameField, emailField, phoneField, addressField, saveBtn, msg, backBtn);
//		layout.setPadding(new Insets(30));
//		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");
//
//		Scene scene = new Scene(layout, 600, 600);
//		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
//		stage.setScene(scene);
//	}
//
//	private void showIssueBook(Stage stage) {
//		TextField bookIdField = createStyledTextField("Book ID");
//		TextField memberIdField = createStyledTextField("Member ID");
//
//		Label msg = new Label();
//		msg.setFont(Font.font("Roboto", 14));
//		msg.setStyle("-fx-text-fill: #e74c3c;");
//
//		Button issueBtn = createStyledButton("Issue Book", e -> {
//			try {
//				Long bookId = Long.parseLong(bookIdField.getText());
//				Long memberId = Long.parseLong(memberIdField.getText());
//				BookLoan loan = bookLoanService.issueBook(bookId, memberId);
//				msg.setText("✅ Book issued successfully! Loan ID: " + loan.getId());
//				msg.setStyle("-fx-text-fill: #2ecc71;");
//			} catch (Exception ex) {
//				msg.setText("❌ Error: " + ex.getMessage());
//				msg.setStyle("-fx-text-fill: #e74c3c;");
//			}
//		});
//
//		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));
//
//		Label issueBookLabel = new Label("📖 Issue Book");
//		issueBookLabel.setFont(Font.font("Roboto", 20));
//		issueBookLabel.setStyle("-fx-text-fill: #2c3e50;");
//
//		VBox layout = new VBox(20, issueBookLabel, bookIdField, memberIdField, issueBtn, msg, backBtn);
//		layout.setPadding(new Insets(30));
//		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");
//
//		Scene scene = new Scene(layout, 600, 500);
//		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
//		stage.setScene(scene);
//	}
//
//	private void showReturnBook(Stage stage) {
//		TextField loanIdField = createStyledTextField("Loan ID");
//
//		Label msg = new Label();
//		msg.setFont(Font.font("Roboto", 14));
//		msg.setStyle("-fx-text-fill: #e74c3c;");
//
//		Button returnBtn = createStyledButton("Return Book", e -> {
//			try {
//				Long loanId = Long.parseLong(loanIdField.getText());
//				BookLoan loan = bookLoanService.returnBook(loanId);
//				msg.setText("✅ Book returned successfully! Loan ID: " + loan.getId());
//				msg.setStyle("-fx-text-fill: #2ecc71;");
//			} catch (Exception ex) {
//				msg.setText("❌ Error: " + ex.getMessage());
//				msg.setStyle("-fx-text-fill: #e74c3c;");
//			}
//		});
//
//		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));
//
//		Label returnBookLabel = new Label("📚 Return Book");
//		returnBookLabel.setFont(Font.font("Roboto", 20));
//		returnBookLabel.setStyle("-fx-text-fill: #2c3e50;");
//
//		VBox layout = new VBox(20, returnBookLabel, loanIdField, returnBtn, msg, backBtn);
//		layout.setPadding(new Insets(30));
//		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");
//
//		Scene scene = new Scene(layout, 600, 400);
//		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
//		stage.setScene(scene);
//	}
//
//	private void showMemberLoans(Stage stage) {
//		TextField memberIdField = createStyledTextField("Member ID");
//
//		ListView<String> listView = new ListView<>();
//		listView.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5;");
//
//		Label msg = new Label();
//		msg.setFont(Font.font("Roboto", 14));
//		msg.setStyle("-fx-text-fill: #e74c3c;");
//
//		Button fetchBtn = createStyledButton("Fetch Loans", e -> {
//			try {
//				Long memberId = Long.parseLong(memberIdField.getText());
//				List<BookLoan> loans = bookLoanService.getLoansByMember(memberId);
//				if (loans.isEmpty()) {
//					listView.setItems(FXCollections.observableArrayList("No loans found for this member."));
//				} else {
//					listView.setItems(FXCollections.observableArrayList(
//							loans.stream().map(l -> "Loan ID: " + l.getId() + " - Book: " + l.getBook().getTitle() +
//									" - Status: " + l.getStatus() + " - Due: " + l.getDueDate()).toList()
//					));
//				}
//				msg.setText("✅ Loans fetched successfully!");
//				msg.setStyle("-fx-text-fill: #2ecc71;");
//			} catch (Exception ex) {
//				msg.setText("❌ Error: " + ex.getMessage());
//				msg.setStyle("-fx-text-fill: #e74c3c;");
//			}
//		});
//
//		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));
//
//		Label memberLoansLabel = new Label("👥 Member Loans");
//		memberLoansLabel.setFont(Font.font("Roboto", 20));
//		memberLoansLabel.setStyle("-fx-text-fill: #2c3e50;");
//
//		VBox layout = new VBox(20, memberLoansLabel, memberIdField, fetchBtn, listView, msg, backBtn);
//		layout.setPadding(new Insets(30));
//		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");
//
//		Scene scene = new Scene(layout, 800, 600);
//		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
//		stage.setScene(scene);
//	}
//
//	private void showOverdueLoans(Stage stage) {
//		List<BookLoan> loans = bookLoanService.getOverdueLoans();
//		ListView<String> listView = new ListView<>();
//		listView.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5;");
//		if (loans.isEmpty()) {
//			listView.setItems(FXCollections.observableArrayList("No overdue loans found."));
//		} else {
//			listView.setItems(FXCollections.observableArrayList(
//					loans.stream().map(l -> "Loan ID: " + l.getId() + " - Book: " + l.getBook().getTitle() +
//							" - Member: " + l.getMember().getName() + " - Due: " + l.getDueDate()).toList()
//			));
//		}
//
//		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));
//
//		Label overdueLoansLabel = new Label("⏰ Overdue Loans");
//		overdueLoansLabel.setFont(Font.font("Roboto", 20));
//		overdueLoansLabel.setStyle("-fx-text-fill: #2c3e50;");
//
//		VBox layout = new VBox(20, overdueLoansLabel, listView, backBtn);
//		layout.setPadding(new Insets(30));
//		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");
//
//		Scene scene = new Scene(layout, 800, 600);
//		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
//		stage.setScene(scene);
//	}
//
//	private Button createStyledButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
//		Button button = new Button(text);
//		button.setFont(Font.font("Roboto", 16));
//		button.setStyle(
//				"-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;"
//		);
//		button.setOnMouseEntered(e -> button.setStyle(
//				"-fx-background-color: #2980b9; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;"
//		));
//		button.setOnMouseExited(e -> button.setStyle(
//				"-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;"
//		));
//		button.setOnAction(action);
//		return button;
//	}
//
//	private TextField createStyledTextField(String prompt) {
//		TextField textField = new TextField();
//		textField.setPromptText(prompt);
//		textField.setFont(Font.font("Roboto", 16));
//		textField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5; -fx-padding: 10;");
//		return textField;
//	}
//
//	@Override
//	public void stop() {
//		context.close();
//	}
//}

package com.lms;

import com.lms.domain.*;
import com.lms.services.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class LibraryManagementSystemApplication extends Application {

	private ConfigurableApplicationContext context;

	private StaffService staffService;
	private BookService bookService;
	private MemberService memberService;
	private BookLoanService bookLoanService;
	private AuthorService authorService;
	private CategoryService categoryService;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() {
		context = new SpringApplicationBuilder(LibraryManagementSystemApplication.class).run();
		staffService = context.getBean(StaffService.class);
		bookService = context.getBean(BookService.class);
		memberService = context.getBean(MemberService.class);
		bookLoanService = context.getBean(BookLoanService.class);
		authorService = context.getBean(AuthorService.class);
		categoryService = context.getBean(CategoryService.class);
	}

	@Override
	public void start(Stage stage) {
		stage.setMaximized(true);
		showLogin(stage);
	}

	private void showLogin(Stage stage) {
		Label title = new Label("📚 Library Management System");
		title.setFont(Font.font("Roboto", 28));
		title.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

		TextField usernameField = new TextField();
		usernameField.setPromptText("Username");
		usernameField.setFont(Font.font("Roboto", 16));
		usernameField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5; -fx-padding: 10;");

		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		passwordField.setFont(Font.font("Roboto", 16));
		passwordField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5; -fx-padding: 10;");

		Label message = new Label();
		message.setFont(Font.font("Roboto", 14));
		message.setStyle("-fx-text-fill: #e74c3c;");

		Button loginBtn = new Button("Login");
		loginBtn.setFont(Font.font("Roboto", 16));
		loginBtn.setStyle(
				"-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;"
		);
		loginBtn.setOnMouseEntered(e -> loginBtn.setStyle(
				"-fx-background-color: #2980b9; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;"
		));
		loginBtn.setOnMouseExited(e -> loginBtn.setStyle(
				"-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;"
		));
		loginBtn.setOnAction(e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();

			Optional<Staff> staffOpt = Optional.ofNullable(staffService.getStaffByUsername(username));
			if (staffOpt.isPresent() && staffOpt.get().getPassword().equals(password)) {
				message.setText("✅ Welcome " + staffOpt.get().getName());
				message.setStyle("-fx-text-fill: #2ecc71;");
				showMainMenu(stage);
			} else {
				message.setText("❌ Invalid username or password!");
				message.setStyle("-fx-text-fill: #e74c3c;");
			}
		});

		VBox loginLayout = new VBox(20, title, usernameField, passwordField, loginBtn, message);
		loginLayout.setAlignment(Pos.CENTER);
		loginLayout.setPadding(new Insets(30));
		loginLayout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(loginLayout, 600, 500);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
		stage.setTitle("Library Login");
		stage.show();
	}

	private void showMainMenu(Stage stage) {
		Label title = new Label("=== Main Menu ===");
		title.setFont(Font.font("Roboto", 24));
		title.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

		Button viewBooksBtn = createStyledButton("View All Books", e -> showBooks(stage));
		Button addBookBtn = createStyledButton("Add Book", e -> showAddBook(stage));
		Button viewMembersBtn = createStyledButton("View Members", e -> showMembers(stage));
		Button addMemberBtn = createStyledButton("Add Member", e -> showAddMember(stage));
		Button issueBookBtn = createStyledButton("Issue Book", e -> showIssueBook(stage));
		Button returnBookBtn = createStyledButton("Return Book", e -> showReturnBook(stage));
		Button viewMemberLoansBtn = createStyledButton("View Member Loans", e -> showMemberLoans(stage));
		Button viewOverdueLoansBtn = createStyledButton("View Overdue Loans", e -> showOverdueLoans(stage));
		Button exitBtn = createStyledButton("Exit", e -> stage.close());

		VBox menuLayout = new VBox(20, title, viewBooksBtn, addBookBtn, viewMembersBtn, addMemberBtn,
				issueBookBtn, returnBookBtn, viewMemberLoansBtn, viewOverdueLoansBtn, exitBtn);
		menuLayout.setAlignment(Pos.CENTER);
		menuLayout.setPadding(new Insets(30));
		menuLayout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(menuLayout, 600, 700);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
		stage.setTitle("Library Main Menu");
	}

	private void showBooks(Stage stage) {
		List<Book> books = bookService.getAllBooks();
		ListView<String> listView = new ListView<>();
		listView.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5;");
		if (books.isEmpty()) {
			listView.setItems(FXCollections.observableArrayList("No books found."));
		} else {
			listView.setItems(FXCollections.observableArrayList(
					books.stream().map(b -> b.getId() + " - " + b.getTitle() +
							" (Available: " + b.getAvailableCopies() + ")").toList()
			));
		}

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label booksLabel = new Label("📖 Books");
		booksLabel.setFont(Font.font("Roboto", 20));
		booksLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, booksLabel, listView, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 800, 600);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
	}

	private void showAddBook(Stage stage) {
		TextField isbnField = createStyledTextField("ISBN");
		TextField titleField = createStyledTextField("Title");
		TextField descField = createStyledTextField("Description");
		TextField yearField = createStyledTextField("Year");
		TextField copiesField = createStyledTextField("Total Copies");

		Label msg = new Label();
		msg.setFont(Font.font("Roboto", 14));
		msg.setStyle("-fx-text-fill: #e74c3c;");

		Button saveBtn = createStyledButton("Save", e -> {
			try {
				Book newBook = Book.builder()
						.isbn(isbnField.getText())
						.title(titleField.getText())
						.description(descField.getText())
						.publicationYear(Integer.parseInt(yearField.getText()))
						.totalCopies(Integer.parseInt(copiesField.getText()))
						.availableCopies(Integer.parseInt(copiesField.getText()))
						.build();
				bookService.addBook(newBook);
				msg.setText("✅ Book added successfully!");
				msg.setStyle("-fx-text-fill: #2ecc71;");
			} catch (Exception ex) {
				msg.setText("❌ Error: " + ex.getMessage());
				msg.setStyle("-fx-text-fill: #e74c3c;");
			}
		});

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label addBookLabel = new Label("➕ Add Book");
		addBookLabel.setFont(Font.font("Roboto", 20));
		addBookLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, addBookLabel, isbnField, titleField, descField, yearField, copiesField, saveBtn, msg, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 600, 600);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
	}

	private void showMembers(Stage stage) {
		List<Member> members = memberService.getAllMembers();
		ListView<String> listView = new ListView<>();
		listView.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5;");
		if (members.isEmpty()) {
			listView.setItems(FXCollections.observableArrayList("No members found."));
		} else {
			listView.setItems(FXCollections.observableArrayList(
					members.stream().map(m -> m.getId() + " - " + m.getName()).toList()
			));
		}

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label membersLabel = new Label("👥 Members");
		membersLabel.setFont(Font.font("Roboto", 20));
		membersLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, membersLabel, listView, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 800, 600);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
	}

	private void showAddMember(Stage stage) {
		TextField idField = createStyledTextField("Member ID");
		TextField nameField = createStyledTextField("Name");
		TextField emailField = createStyledTextField("Email");
		TextField phoneField = createStyledTextField("Phone");
		TextField addressField = createStyledTextField("Address");

		Label msg = new Label();
		msg.setFont(Font.font("Roboto", 14));
		msg.setStyle("-fx-text-fill: #e74c3c;");

		Button saveBtn = createStyledButton("Save", e -> {
			try {
				Member member = Member.builder()
						.memberId(idField.getText())
						.name(nameField.getText())
						.email(emailField.getText())
						.phoneNumber(phoneField.getText())
						.address(addressField.getText())
						.build();
				memberService.registerMember(member);
				msg.setText("✅ Member added successfully!");
				msg.setStyle("-fx-text-fill: #2ecc71;");
			} catch (Exception ex) {
				msg.setText("❌ Error: " + ex.getMessage());
				msg.setStyle("-fx-text-fill: #e74c3c;");
			}
		});

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label addMemberLabel = new Label("➕ Add Member");
		addMemberLabel.setFont(Font.font("Roboto", 20));
		addMemberLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, addMemberLabel, idField, nameField, emailField, phoneField, addressField, saveBtn, msg, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 600, 600);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
	}

	private void showIssueBook(Stage stage) {
		TextField bookIdField = createStyledTextField("Book ID");
		TextField memberIdField = createStyledTextField("Member ID");

		Label msg = new Label();
		msg.setFont(Font.font("Roboto", 14));
		msg.setStyle("-fx-text-fill: #e74c3c;");

		Button issueBtn = createStyledButton("Issue Book", e -> {
			try {
				Long bookId = Long.parseLong(bookIdField.getText());
				Long memberId = Long.parseLong(memberIdField.getText());
				BookLoan loan = bookLoanService.issueBook(bookId, memberId);
				msg.setText("✅ Book issued successfully! Loan ID: " + loan.getId());
				msg.setStyle("-fx-text-fill: #2ecc71;");
			} catch (Exception ex) {
				msg.setText("❌ Error: " + ex.getMessage());
				msg.setStyle("-fx-text-fill: #e74c3c;");
			}
		});

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label issueBookLabel = new Label("📖 Issue Book");
		issueBookLabel.setFont(Font.font("Roboto", 20));
		issueBookLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, issueBookLabel, bookIdField, memberIdField, issueBtn, msg, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 600, 500);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
	}

	private void showReturnBook(Stage stage) {
		TextField loanIdField = createStyledTextField("Loan ID");

		Label msg = new Label();
		msg.setFont(Font.font("Roboto", 14));
		msg.setStyle("-fx-text-fill: #e74c3c;");

		Button returnBtn = createStyledButton("Return Book", e -> {
			try {
				Long loanId = Long.parseLong(loanIdField.getText());
				BookLoan loan = bookLoanService.returnBook(loanId);
				msg.setText("✅ Book returned successfully! Loan ID: " + loan.getId() + ", Book: " +
						(loan.getBook() != null ? loan.getBook().getTitle() : "Unknown"));
				msg.setStyle("-fx-text-fill: #2ecc71;");
			} catch (Exception ex) {
				msg.setText("❌ Error: " + ex.getMessage());
				msg.setStyle("-fx-text-fill: #e74c3c;");
			}
		});

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label returnBookLabel = new Label("📚 Return Book");
		returnBookLabel.setFont(Font.font("Roboto", 20));
		returnBookLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, returnBookLabel, loanIdField, returnBtn, msg, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 600, 400);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
	}

	private void showMemberLoans(Stage stage) {
		TextField memberIdField = createStyledTextField("Member ID");

		ListView<String> listView = new ListView<>();
		listView.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5;");

		Label msg = new Label();
		msg.setFont(Font.font("Roboto", 14));
		msg.setStyle("-fx-text-fill: #e74c3c;");

		Button fetchBtn = createStyledButton("Fetch Loans", e -> {
			try {
				Long memberId = Long.parseLong(memberIdField.getText());
				List<BookLoan> loans = bookLoanService.getLoansByMember(memberId);
				if (loans.isEmpty()) {
					listView.setItems(FXCollections.observableArrayList("No loans found for this member."));
				} else {
					listView.setItems(FXCollections.observableArrayList(
							loans.stream().map(l -> String.format(
									"Loan ID: %d - Book: %s - Status: %s - Due: %s",
									l.getId(),
									l.getBook() != null ? l.getBook().getTitle() : "Unknown",
									l.getStatus(),
									l.getDueDate()
							)).toList()
					));
				}
				msg.setText("✅ Loans fetched successfully!");
				msg.setStyle("-fx-text-fill: #2ecc71;");
			} catch (Exception ex) {
				msg.setText("❌ Error: " + ex.getMessage());
				msg.setStyle("-fx-text-fill: #e74c3c;");
				listView.setItems(FXCollections.observableArrayList("Failed to fetch loans."));
			}
		});

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label memberLoansLabel = new Label("👥 Member Loans");
		memberLoansLabel.setFont(Font.font("Roboto", 20));
		memberLoansLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, memberLoansLabel, memberIdField, fetchBtn, listView, msg, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 800, 600);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
	}

	private void showOverdueLoans(Stage stage) {
		List<BookLoan> loans = bookLoanService.getOverdueLoans();
		ListView<String> listView = new ListView<>();
		listView.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5;");
		if (loans.isEmpty()) {
			listView.setItems(FXCollections.observableArrayList("No overdue loans found."));
		} else {
			listView.setItems(FXCollections.observableArrayList(
					loans.stream().map(l -> String.format(
							"Loan ID: %d - Book: %s - Member: %s - Due: %s",
							l.getId(),
							l.getBook() != null ? l.getBook().getTitle() : "Unknown",
							l.getMember() != null ? l.getMember().getName() : "Unknown",
							l.getDueDate()
					)).toList()
			));
		}

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label overdueLoansLabel = new Label("⏰ Overdue Loans");
		overdueLoansLabel.setFont(Font.font("Roboto", 20));
		overdueLoansLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, overdueLoansLabel, listView, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 800, 600);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
	}

	private Button createStyledButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
		Button button = new Button(text);
		button.setFont(Font.font("Roboto", 16));
		button.setStyle(
				"-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;"
		);
		button.setOnMouseEntered(e -> button.setStyle(
				"-fx-background-color: #2980b9; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;"
		));
		button.setOnMouseExited(e -> button.setStyle(
				"-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;"
		));
		button.setOnAction(action);
		return button;
	}

	private TextField createStyledTextField(String prompt) {
		TextField textField = new TextField();
		textField.setPromptText(prompt);
		textField.setFont(Font.font("Roboto", 16));
		textField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5; -fx-padding: 10;");
		return textField;
	}

	@Override
	public void stop() {
		context.close();
	}
}