
package com.lms;

import com.lms.domain.*;
import com.lms.services.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
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

import java.util.ArrayList;
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
	private PublisherService publisherService;

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
		publisherService = context.getBean(PublisherService.class);
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
		stage.setMaximized(true);
		stage.show();
	}

	private void showMainMenu(Stage stage) {
		Label title = new Label("=== Main Menu ===");
		title.setFont(Font.font("Roboto", 24));
		title.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

		// Create buttons
		Button viewBooksBtn = createStyledButton("View All Books", e -> showBooks(stage));
		Button addBookBtn = createStyledButton("Add Book", e -> showAddBook(stage));
		Button updateBookBtn = createStyledButton("Update Book", e -> showUpdateBook(stage));
		Button deleteBookBtn = createStyledButton("Delete Book", e -> showDeleteBook(stage));
		Button viewMembersBtn = createStyledButton("View Members", e -> showMembers(stage));
		Button addMemberBtn = createStyledButton("Add Member", e -> showAddMember(stage));
		Button deleteMemberBtn = createStyledButton("Delete Member", e -> showDeleteMember(stage));
		Button issueBookBtn = createStyledButton("Issue Book", e -> showIssueBook(stage));
		Button returnBookBtn = createStyledButton("Return Book", e -> showReturnBook(stage));
		Button viewAllLoansBtn = createStyledButton("View All Loans", e -> showAllLoans(stage));
		Button viewMemberLoansBtn = createStyledButton("View Member Loans", e -> showMemberLoans(stage));
		Button viewOverdueLoansBtn = createStyledButton("View Overdue Loans", e -> showOverdueLoans(stage));
		Button viewAuthorsBtn = createStyledButton("View Authors", e -> showAuthors(stage));
		Button viewPublishersBtn = createStyledButton("View Publishers", e -> showPublishers(stage));
		Button viewCategoriesBtn = createStyledButton("View Categories", e -> showCategories(stage));
		Button searchByCategoryBtn = createStyledButton("Search Books by Category", e -> showSearchBooksByCategory(stage));
		Button exitBtn = createStyledButton("Exit", e -> stage.close());

		// Create a GridPane for buttons
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(20);
		gridPane.setVgap(20);
		gridPane.setPadding(new Insets(20));

		// Add buttons to the grid (2 columns)
		gridPane.add(viewBooksBtn, 0, 0);
		gridPane.add(addBookBtn, 1, 0);
		gridPane.add(updateBookBtn, 0, 1);
		gridPane.add(deleteBookBtn, 1, 1);
		gridPane.add(viewMembersBtn, 0, 2);
		gridPane.add(addMemberBtn, 1, 2);
		gridPane.add(deleteMemberBtn, 0, 3);
		gridPane.add(issueBookBtn, 1, 3);
		gridPane.add(returnBookBtn, 0, 4);
		gridPane.add(viewAllLoansBtn, 1, 4);
		gridPane.add(viewMemberLoansBtn, 0, 5);
		gridPane.add(viewOverdueLoansBtn, 1, 5);
		gridPane.add(viewAuthorsBtn, 0, 6);
		gridPane.add(viewPublishersBtn, 1, 6);
		gridPane.add(viewCategoriesBtn, 0, 7);
		gridPane.add(searchByCategoryBtn, 1, 7);
		gridPane.add(exitBtn, 0, 8, 2, 1); // Span exit button across 2 columns

		// Ensure buttons take up full width in their cells
		viewBooksBtn.setMaxWidth(Double.MAX_VALUE);
		addBookBtn.setMaxWidth(Double.MAX_VALUE);
		updateBookBtn.setMaxWidth(Double.MAX_VALUE);
		deleteBookBtn.setMaxWidth(Double.MAX_VALUE);
		viewMembersBtn.setMaxWidth(Double.MAX_VALUE);
		addMemberBtn.setMaxWidth(Double.MAX_VALUE);
		deleteMemberBtn.setMaxWidth(Double.MAX_VALUE);
		issueBookBtn.setMaxWidth(Double.MAX_VALUE);
		returnBookBtn.setMaxWidth(Double.MAX_VALUE);
		viewAllLoansBtn.setMaxWidth(Double.MAX_VALUE);
		viewMemberLoansBtn.setMaxWidth(Double.MAX_VALUE);
		viewOverdueLoansBtn.setMaxWidth(Double.MAX_VALUE);
		viewAuthorsBtn.setMaxWidth(Double.MAX_VALUE);
		viewPublishersBtn.setMaxWidth(Double.MAX_VALUE);
		viewCategoriesBtn.setMaxWidth(Double.MAX_VALUE);
		searchByCategoryBtn.setMaxWidth(Double.MAX_VALUE);
		exitBtn.setMaxWidth(Double.MAX_VALUE);

		// Center the exit button
		GridPane.setHalignment(exitBtn, HPos.CENTER);

		// Create layout with title and grid
		VBox layout = new VBox(20, title, gridPane);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		// Wrap in ScrollPane to handle overflow
		ScrollPane scrollPane = new ScrollPane(layout);
		scrollPane.setFitToWidth(true);
		scrollPane.setStyle("-fx-background: #ecf0f1; -fx-border-color: transparent;");

		Scene scene = new Scene(scrollPane, 600, 600);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
		stage.setTitle("Library Main Menu");
		stage.setMaximized(true);
	}

	private Button createStyledButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
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
		button.setOnAction(handler);
		return button;
	}

	private TextField createStyledTextField(String prompt) {
		TextField textField = new TextField();
		textField.setPromptText(prompt);
		textField.setFont(Font.font("Roboto", 16));
		textField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5; -fx-padding: 10;");
		return textField;
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
		stage.setMaximized(true);
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
		stage.setMaximized(true);
	}

	private void showUpdateBook(Stage stage) {
		TextField bookIdField = createStyledTextField("Book ID");
		TextField isbnField = createStyledTextField("ISBN");
		TextField titleField = createStyledTextField("Title");
		TextField descField = createStyledTextField("Description");
		TextField yearField = createStyledTextField("Year");
		TextField copiesField = createStyledTextField("Total Copies");

		Label msg = new Label();
		msg.setFont(Font.font("Roboto", 14));
		msg.setStyle("-fx-text-fill: #e74c3c;");

		Button loadBtn = createStyledButton("Load Book", e -> {
			try {
				Long bookId = Long.parseLong(bookIdField.getText());
				Optional<Book> bookOpt = bookService.getBookById(bookId);
				if (bookOpt.isPresent()) {
					Book book = bookOpt.get();
					isbnField.setText(book.getIsbn());
					titleField.setText(book.getTitle());
					descField.setText(book.getDescription());
					yearField.setText(String.valueOf(book.getPublicationYear()));
					copiesField.setText(String.valueOf(book.getTotalCopies()));
					msg.setText("✅ Book loaded successfully!");
					msg.setStyle("-fx-text-fill: #2ecc71;");
				} else {
					msg.setText("❌ Book not found!");
					msg.setStyle("-fx-text-fill: #e74c3c;");
				}
			} catch (NumberFormatException ex) {
				msg.setText("❌ Invalid Book ID! Please enter a valid number.");
				msg.setStyle("-fx-text-fill: #e74c3c;");
			} catch (Exception ex) {
				msg.setText("❌ Error: " + ex.getMessage());
				msg.setStyle("-fx-text-fill: #e74c3c;");
			}
		});

		Button saveBtn = createStyledButton("Update", e -> {
			try {
				Long bookId = Long.parseLong(bookIdField.getText());
				Optional<Book> bookOpt = bookService.getBookById(bookId);
				if (bookOpt.isPresent()) {
					Book book = bookOpt.get();
					book.setIsbn(isbnField.getText());
					book.setTitle(titleField.getText());
					book.setDescription(descField.getText());
					book.setPublicationYear(Integer.parseInt(yearField.getText()));
					int totalCopies = Integer.parseInt(copiesField.getText());
					if (totalCopies < book.getTotalCopies() - book.getAvailableCopies()) {
						throw new IllegalStateException("Total copies cannot be less than borrowed copies!");
					}
					book.setTotalCopies(totalCopies);
					book.setAvailableCopies(book.getAvailableCopies() + (totalCopies - book.getTotalCopies()));
					bookService.updateBook(book);
					msg.setText("✅ Book updated successfully!");
					msg.setStyle("-fx-text-fill: #2ecc71;");
				} else {
					msg.setText("❌ Book not found!");
					msg.setStyle("-fx-text-fill: #e74c3c;");
				}
			} catch (NumberFormatException ex) {
				msg.setText("❌ Invalid input! Year and copies must be numbers.");
				msg.setStyle("-fx-text-fill: #e74c3c;");
			} catch (IllegalStateException ex) {
				msg.setText("❌ Error: " + ex.getMessage());
				msg.setStyle("-fx-text-fill: #e74c3c;");
			} catch (Exception ex) {
				msg.setText("❌ Error: " + ex.getMessage());
				msg.setStyle("-fx-text-fill: #e74c3c;");
			}
		});

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label updateBookLabel = new Label("✏️ Update Book");
		updateBookLabel.setFont(Font.font("Roboto", 20));
		updateBookLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, updateBookLabel, bookIdField, loadBtn, isbnField, titleField, descField, yearField, copiesField, saveBtn, msg, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 600, 700);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
		stage.setMaximized(true);
	}

	private void showDeleteBook(Stage stage) {
		TextField bookIdField = createStyledTextField("Book ID");

		Label msg = new Label();
		msg.setFont(Font.font("Roboto", 14));
		msg.setStyle("-fx-text-fill: #e74c3c;");

		Button deleteBtn = createStyledButton("Delete", e -> {
			try {
				Long bookId = Long.parseLong(bookIdField.getText());
				bookService.deleteBook(bookId);
				msg.setText("✅ Book deleted successfully!");
				msg.setStyle("-fx-text-fill: #2ecc71;");
			} catch (Exception ex) {
				msg.setText("❌ Error: " + ex.getMessage());
				msg.setStyle("-fx-text-fill: #e74c3c;");
			}
		});

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label deleteBookLabel = new Label("🗑️ Delete Book");
		deleteBookLabel.setFont(Font.font("Roboto", 20));
		deleteBookLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, deleteBookLabel, bookIdField, deleteBtn, msg, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 600, 400);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
		stage.setMaximized(true);
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
		stage.setMaximized(true);
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
		stage.setMaximized(true);
	}

	private void showDeleteMember(Stage stage) {
		TextField memberIdField = createStyledTextField("Member ID");

		Label msg = new Label();
		msg.setFont(Font.font("Roboto", 14));
		msg.setStyle("-fx-text-fill: #e74c3c;");

		Button deleteBtn = createStyledButton("Delete", e -> {
			try {
				Long memberId = Long.parseLong(memberIdField.getText());
				memberService.deleteMember(memberId);
				msg.setText("✅ Member deleted successfully!");
				msg.setStyle("-fx-text-fill: #2ecc71;");
			} catch (Exception ex) {
				msg.setText("❌ Error: " + ex.getMessage());
				msg.setStyle("-fx-text-fill: #e74c3c;");
			}
		});

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label deleteMemberLabel = new Label("🗑️ Delete Member");
		deleteMemberLabel.setFont(Font.font("Roboto", 20));
		deleteMemberLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, deleteMemberLabel, memberIdField, deleteBtn, msg, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 600, 400);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
		stage.setMaximized(true);
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
		stage.setMaximized(true);
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
		stage.setMaximized(true);
	}

	private void showAllLoans(Stage stage) {
		TextField memberIdField = createStyledTextField("Member ID (optional)");
		ComboBox<String> statusCombo = new ComboBox<>(FXCollections.observableArrayList("All", "ISSUED", "RETURNED"));
		statusCombo.setValue("All");

		ListView<String> listView = new ListView<>();
		listView.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5;");

		Label msg = new Label();
		msg.setFont(Font.font("Roboto", 14));
		msg.setStyle("-fx-text-fill: #e74c3c;");

		Button fetchBtn = createStyledButton("Fetch Loans", e -> {
			try {
				List<BookLoan> loans = bookLoanService.getAllLoans();
				if (!memberIdField.getText().isEmpty()) {
					Long memberId = Long.parseLong(memberIdField.getText());
					loans = loans.stream().filter(l -> l.getMember() != null && l.getMember().getId().equals(memberId)).toList();
				}
				String status = statusCombo.getValue();
				if (!"All".equals(status)) {
					loans = loans.stream().filter(l -> l.getStatus().toString().equals(status)).toList();
				}
				if (loans.isEmpty()) {
					listView.setItems(FXCollections.observableArrayList("No loans found."));
				} else {
					listView.setItems(FXCollections.observableArrayList(
							loans.stream().map(l -> String.format(
									"Loan ID: %d - Book: %s - Member: %s - Status: %s - Due: %s",
									l.getId(),
									l.getBook() != null ? l.getBook().getTitle() : "Unknown",
									l.getMember() != null ? l.getMember().getName() : "Unknown",
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

		Label allLoansLabel = new Label("📋 All Loans");
		allLoansLabel.setFont(Font.font("Roboto", 20));
		allLoansLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, allLoansLabel, memberIdField, statusCombo, fetchBtn, listView, msg, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 800, 600);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
		stage.setMaximized(true);
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
		stage.setMaximized(true);
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
		stage.setMaximized(true);
	}

	private void showAuthors(Stage stage) {
		List<Author> authors = authorService.getAllAuthors();
		ListView<String> listView = new ListView<>();
		listView.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5;");
		List<String> items = new ArrayList<>();
		if (authors.isEmpty()) {
			items.add("No authors found.");
		} else {
			for (Author author : authors) {
				items.add("Author ID: " + author.getId() + " - Name: " + author.getName());
				List<Book> books = author.getBooks();
				if (books.isEmpty()) {
					items.add("  - No books");
				} else {
					for (Book book : books) {
						items.add("  - " + book.getTitle() + " (ID: " + book.getId() + ")");
					}
				}
			}
		}
		listView.setItems(FXCollections.observableArrayList(items));

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label authorsLabel = new Label("📝 Authors");
		authorsLabel.setFont(Font.font("Roboto", 20));
		authorsLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, authorsLabel, listView, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 800, 600);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
		stage.setMaximized(true);
	}

	private void showPublishers(Stage stage) {
		List<Publisher> publishers = publisherService.getAllPublishers();
		ListView<String> listView = new ListView<>();
		listView.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5;");
		List<String> items = new ArrayList<>();
		if (publishers.isEmpty()) {
			items.add("No publishers found.");
		} else {
			for (Publisher publisher : publishers) {
				items.add("Publisher ID: " + publisher.getId() + " - Name: " + publisher.getName());
				List<Book> books = publisher.getBooks();
				if (books.isEmpty()) {
					items.add("  - No books");
				} else {
					for (Book book : books) {
						items.add("  - " + book.getTitle() + " (ID: " + book.getId() + ")");
					}
				}
			}
		}
		listView.setItems(FXCollections.observableArrayList(items));

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label publishersLabel = new Label("🏢 Publishers");
		publishersLabel.setFont(Font.font("Roboto", 20));
		publishersLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, publishersLabel, listView, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 800, 600);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
		stage.setMaximized(true);
	}

	private void showCategories(Stage stage) {
		List<Category> categories = categoryService.getAllCategories();
		ListView<String> listView = new ListView<>();
		listView.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5;");
		List<String> items = new ArrayList<>();
		if (categories.isEmpty()) {
			items.add("No categories found.");
		} else {
			for (Category category : categories) {
				items.add("Category ID: " + category.getId() + " - Name: " + category.getName());
				List<Book> books = category.getBooks();
				if (books.isEmpty()) {
					items.add("  - No books");
				} else {
					for (Book book : books) {
						items.add("  - " + book.getTitle() + " (ID: " + book.getId() + ")");
					}
				}
			}
		}
		listView.setItems(FXCollections.observableArrayList(items));

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label categoriesLabel = new Label("📚 Categories");
		categoriesLabel.setFont(Font.font("Roboto", 20));
		categoriesLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, categoriesLabel, listView, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 800, 600);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
		stage.setMaximized(true);
	}

	private void showSearchBooksByCategory(Stage stage) {
		TextField categoryIdField = createStyledTextField("Category ID");

		ListView<String> listView = new ListView<>();
		listView.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5;");

		Label msg = new Label();
		msg.setFont(Font.font("Roboto", 14));
		msg.setStyle("-fx-text-fill: #e74c3c;");

		Button fetchBtn = createStyledButton("Search", e -> {
			try {
				Long categoryId = Long.parseLong(categoryIdField.getText());
				List<Book> books = bookService.getBooksByCategory(categoryId);
				if (books.isEmpty()) {
					listView.setItems(FXCollections.observableArrayList("No books found for this category."));
				} else {
					listView.setItems(FXCollections.observableArrayList(
							books.stream().map(b -> b.getId() + " - " + b.getTitle() +
									" (Available: " + b.getAvailableCopies() + ")").toList()
					));
				}
				msg.setText("✅ Books fetched successfully!");
				msg.setStyle("-fx-text-fill: #2ecc71;");
			} catch (Exception ex) {
				msg.setText("❌ Error: " + ex.getMessage());
				msg.setStyle("-fx-text-fill: #e74c3c;");
				listView.setItems(FXCollections.observableArrayList("Failed to fetch books."));
			}
		});

		Button backBtn = createStyledButton("Back", e -> showMainMenu(stage));

		Label searchLabel = new Label("🔍 Search Books by Category");
		searchLabel.setFont(Font.font("Roboto", 20));
		searchLabel.setStyle("-fx-text-fill: #2c3e50;");

		VBox layout = new VBox(20, searchLabel, categoryIdField, fetchBtn, listView, msg, backBtn);
		layout.setPadding(new Insets(30));
		layout.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 10; -fx-background-radius: 10;");

		Scene scene = new Scene(layout, 800, 600);
		scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
		stage.setScene(scene);
		stage.setMaximized(true);
	}

	@Override
	public void stop() {
		context.close();
	}
}