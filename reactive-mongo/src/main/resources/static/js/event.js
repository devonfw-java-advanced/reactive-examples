const evtSource = new EventSource("product/stream");
evtSource.onmessage = (event) => {
    const newElement = document.createElement("li");
    const eventList = document.getElementById("product_list");

    newElement.textContent = `message: ${event.data}`;
    eventList.appendChild(newElement);
}