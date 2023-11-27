const baseUrl = "http://localhost:8080/api/v1/photos";
const app = document.querySelector("#app");

const getPhotos = async () => {
    try {
        const response = await fetch(baseUrl);
        const data = await response.json();
        renderPhotos(data, app);
    } catch (error) {
        console.log(error);
    }
}

const renderPhotos = (data, index) => {
    let content;
    if (data.length > 0) {
        content = `<div class="row row-cols-3 g-3">`;
        data.forEach(photo => {
            console.log(photo.categories);
            content +=
                `<div class="card bg-light-subtle shadow">
                        <img src="" class="card-img-top" alt="${photo.title}">
                        <div class="card-body">
                            <h5 class="card-title">${photo.title}</h5>
                            <p class="card-text">${photo.description}</p>
                        </div>
                        ${renderCategories(photo.categories)}
                    </div>`;
        });
        content += `</div>`;
    } else {
        content = `<div class="alert">No photos available</div>`;
    }
    index.innerHTML = content;
}

const renderCategories = categories => {
    let content;
    content = `<ul class="list-group list-group-flush">`;
    if (categories.length > 0) {
        categories.forEach(category => {
            content += `<li class="list-group-item bg-light-subtle">${category.name}</li>`;
        });
    } else {
        content += `<li class="list-group-item bg-light-subtle">No categories</li>`;
    }
    content += `</ul>`;
    return content;
}

getPhotos();