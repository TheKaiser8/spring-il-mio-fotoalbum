/* CONSTANTS */
const PHOTOS_API_URL = "http://localhost:8080/api/v1/photos";
const contentDOM = document.getElementById("content");

/* API */
const getPhotos = async (keyword) => {
  try {
    const response = await axios.get(PHOTOS_API_URL, {
      params: {
        keyword: keyword,
      },
    });
    console.log(response);
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

/* DOM MANIPULATION */
// prende in ingresso i dati e restituisce una stringa con la lista deLle foto
const createPhotoList = (data) => {
  if (data.length > 0) {
    let list = `<div class="row">`;
    // itero sulla lista
    data.forEach((element) => {
      list += createPhotoCard(element);
    });
    list += `</div>`;
    return list;
  } else {
    return `<div class="alert alert-info">Empty List</div>`;
  }
};

const createPhotoCard = function (photo) {
  // path immagine
  const imageUrl = photo.urlUpload ? `files/url/${photo.id}` : photo.url;

  // categorie
  let categories = "";
  if (photo.categories.length > 0) {
    photo.categories.forEach((cat) => {
      categories += `<span class="badge text-bg-secondary m-1">${cat.name}</span>`;
    });
  }

  // visibilità
  const visibilityBadge = photo.visible
    ? '<span class="badge text-bg-success m-1">Pubblica</span>'
    : '<span class="badge text-bg-danger m-1">Privata</span>';

  return `<div class="d-flex justify-content-center col-md-6 col-lg-4 mb-5">
            <div class="card h-100 w-100" style="max-width: 20rem;">
              <img src="http://localhost:8080/${imageUrl}" class="card-img-top img-thumbnail img-fluid" style="max-height: 18rem;" alt="${photo.title}">
              <div class="card-body d-flex flex-column">
                <div class="mb-3">
                  <h5 class="card-title">${photo.title}</h5>
                  <p class="card-text text-truncate">${photo.description}</p>
                </div>
                <div class="mb-3">
                  <strong>Categorie:</strong>
                  ${categories}
                </div>
                <div class="text-center mb-3">
                  ${visibilityBadge}
                </div>
              </div>
            </div>
          </div>`;
};

const loadData = async (keyword = "") => {
  // prendo i dati dall'API
  const data = await getPhotos(keyword);
  // con quei dati costruisco il content
  // appendo il contente nel DOM
  contentDOM.innerHTML = createPhotoList(data);
};

// gestore eventi form di ricerca
const searchForm = document.getElementById("searchForm");
searchForm.addEventListener("submit", async (event) => {
  event.preventDefault();
  const searchInput = document.getElementById("searchInput");
  const keyword = searchInput.value;

  await loadData(keyword);
});

/* CODICE GLOBALE */
loadData();
