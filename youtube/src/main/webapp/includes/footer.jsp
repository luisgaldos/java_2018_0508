
	<!-- Footer -->
	<footer class="bg-pika text-pika p-3">
		<c:set var="anyo" value="<%= new java.util.Date() %>"/>
		<p class="text-center">Copyright &copy; Adrian Perozzo <fmt:formatDate type="both" dateStyle="medium" value="${anyo}"/></p>
	</footer>
   <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

  </body>

</html>