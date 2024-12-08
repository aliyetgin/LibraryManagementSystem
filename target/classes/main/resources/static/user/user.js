// Fetch all books from the backend
async function fetchBooks() {
    const response = await fetch('/books');
    const books = await response.json();
    const bookList = document.getElementById('book-list');
    bookList.innerHTML = ''; // Clear the list before appending new items

    books.forEach(book => {
        const li = document.createElement('li');
        li.textContent = `${book.title} by ${book.author} (Genre: ${book.genre}, Quantity: ${book.quantity})`;
        bookList.appendChild(li);
    });
}

// Search for books by title or author
async function searchBooks() {
    const title = document.getElementById('search-title').value;
    const author = document.getElementById('search-author').value;

    let url = '/books'; // Default endpoint to fetch all books
    if (title || author) {
        const params = new URLSearchParams();
        if (title) params.append('title', title);
        if (author) params.append('author', author);
        url = `/books/search?${params.toString()}`; // Adjust to your backend search endpoint
    }

    const response = await fetch(url);
    const books = await response.json();
    const bookList = document.getElementById('book-list');
    bookList.innerHTML = ''; // Clear the list before appending new items

    if (books.length === 0) {
        bookList.innerHTML = '<li>No books found</li>';
        return;
    }

    books.forEach(book => {
        const li = document.createElement('li');
        li.textContent = `${book.title} by ${book.author} (Genre: ${book.genre}, Quantity: ${book.quantity})`;
        bookList.appendChild(li);
    });
}

// Add event listener for search functionality
document.getElementById('search-title').addEventListener('keyup', searchBooks);
document.getElementById('search-author').addEventListener('keyup', searchBooks);

// Load available books when the page loads
document.addEventListener('DOMContentLoaded', fetchBooks);
