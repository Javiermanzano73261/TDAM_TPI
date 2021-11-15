# TDAM_TPI
Para que sea funcional debe reemplazar en la clase AppRepository 
las urls de los siguientes métodos:

1° getApiData (Obtiene el listado de albums de un usuario)


- Puede encontrar el link aquí:
  https://www.flickr.com/services/api/explore/flickr.photosets.getList

- Utilice el parámetro Your user ID: 193998612@N06

- Debe configurar el output como JSON y 
  seleccionando la opción "Do not sign call?"

- Al hacer click en "Call method obtendrá el link a remplazar en la 
  variable url del método (línea 201)

2° getApiPhotosDeAlbum (obtiene el listado de fotos de un  album)

- Puede encontrar el link aquí:
  https://www.flickr.com/services/api/explore/flickr.photosets.getPhotos

- Utilice el parámetro Your user ID: 193998612@N06
- Utilice el parámetro Photo set ID:51681437762

- Debe configurar el output como JSON y 
  seleccionando la opción "Do not sign call?"

- Al hacer click en "Call method" obtendrá el link, solo debe remplazar en la 
  en variable url del método (línea 247) la primera parte del link, hasta finalizar
  el "photoset_id=" sin incluir el ID del photoset.
  Ejemplo: https://www.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=69a1e3b4fc4220680ad044ba696b0e09&photoset_id=

3° getApiComent (Obtiene los comentarios de una foto)

- Puede encontrar el link aquí:
  https://www.flickr.com/services/api/explore/flickr.photos.comments.getList

- Utilice el parámetro Photo ID:51681437762

- Debe configurar el output como JSON y 
  seleccionando la opción "Do not sign call?"

- Al hacer click en "Call method" obtendrá el link, solo debe remplazar en la 
  en variable url del método (línea 297) la primera parte del link, hasta finalizar
  el "photo_id=" sin incluir el ID de la foto.
  Ejemplo: https://www.flickr.com/services/rest/?method=flickr.photos.comments.getList&api_key=69a1e3b4fc4220680ad044ba696b0e09&photo_id=
