// Fetch books from the backend
async function fetchBooks() {
    const response = await fetch('/books');
    const books = await response.json();
    const bookList = document.getElementById('book-list');
    bookList.innerHTML = '';  // Clear the list before appending new items
    books.forEach(book => {
        const li = document.createElement('li');
        li.textContent = `${book.title} by ${book.author} (Quantity: ${book.quantity})`;
        bookList.appendChild(li);
    });
}

// Add new book to the backend
document.getElementById('add-book-form').addEventListener('submit', async (e) => {
    document.getElementById('add-book-form').addEventListener('submit', async (e) => {
        e.preventDefault();
         const title = document.getElementById('title').value;
            const author = document.getElementById('author').value;
            const genre = document.getElementById('genre').value;  // Get genre value
            const quantity = document.getElementById('quantity').value;

            const response = await fetch('/books', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ title, author, genre, quantity })  // Send genre in the request body
            });

        const message = await response.text();
        alert(message);  // Show the response from the backend
        fetchBooks();  // Refresh the book list
    });

});
