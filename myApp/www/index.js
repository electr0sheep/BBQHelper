function startJS() {
  var b = document.getElementById('select').value;
  var url = 'record.html?name=' + encodeURIComponent(b);

  document.location.href = url;
}
