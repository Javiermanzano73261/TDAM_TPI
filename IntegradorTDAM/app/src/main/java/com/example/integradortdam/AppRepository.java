package com.example.integradortdam;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.integradortdam.entities.AlbumModel;
import com.example.integradortdam.entities.ComentarioModel;
import com.example.integradortdam.entities.FotoModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class AppRepository implements Serializable {
    private AlbumDAO mAlbumDao;
    private FotoDAO mFotoDao;
    private ComentarioDAO mComentarioDao;
    private ArrayList<AlbumModel> mAllAlbums = new ArrayList<>();
    private ArrayList<FotoModel> mAllFotos = new ArrayList<>();
    private ArrayList<ComentarioModel> mAllComentarios = new ArrayList<>();
    private boolean firstTime = false;
    private int contador;
    private int total;


    private MainActivity mainActivity;
    private SecondActivity secondActivity;
    private ThirdActivity thirdActivity;

    private Application application;



    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    AppRepository(Application application, MainActivity activity)  {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        mAlbumDao = db.albumDao();
        mFotoDao = db.fotoDao();
        mComentarioDao = db.comentarioDao();
        this.mainActivity = activity;
        this.application = application;
        firstTime = true;
        obtenerData();
    }

    AppRepository(Application application)  {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        mAlbumDao = db.albumDao();
        mFotoDao = db.fotoDao();
        mComentarioDao = db.comentarioDao();
    }


    public void setMainActivity(MainActivity mainActivity) { this.mainActivity = mainActivity; }
    public void setSecondActivity(SecondActivity secondActivity) { this.secondActivity = secondActivity; }
    public void setThirdActivity(ThirdActivity thirdActivity) { this.thirdActivity = thirdActivity; }

    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) application.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public void obtenerData() {
        total = 3;
        try {
            mAllAlbums = getAllAlbums();
            mAllFotos = getAllFotos();
            mAllComentarios = getAllComentarios();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isConnected()) {
            deleteAll();
            getApiData(); }
    }



    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.

    private  ArrayList<AlbumModel> getAllAlbums() throws ExecutionException, InterruptedException {
    Callable<ArrayList<AlbumModel>> callable = new Callable<ArrayList<AlbumModel>>() {
        @Override
        public ArrayList<AlbumModel> call() throws Exception {
            mAllAlbums = (ArrayList<AlbumModel>) mAlbumDao.getAllAlbums();
            contador += 1;
            if(contador == total){ mainActivity.setAlbums(mAllAlbums);}
            return mAllAlbums;
        }
    };
        Future<ArrayList<AlbumModel>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    private  ArrayList<FotoModel> getAllFotos() throws ExecutionException, InterruptedException {
        Callable<ArrayList<FotoModel>> callable = new Callable<ArrayList<FotoModel>>() {
            @Override
            public ArrayList<FotoModel> call() throws Exception {
                mAllFotos = (ArrayList<FotoModel>) mFotoDao.getAllFotos();
                contador += 1;
                if(contador == total){ mainActivity.setAlbums(mAllAlbums);}
                return mAllFotos;
            }
        };
        Future<ArrayList<FotoModel>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    private  ArrayList<ComentarioModel> getAllComentarios() throws ExecutionException, InterruptedException {
        Callable<ArrayList<ComentarioModel>> callable = new Callable<ArrayList<ComentarioModel>>() {
            @Override
            public ArrayList<ComentarioModel> call() throws Exception {
                mAllComentarios.addAll(mComentarioDao.getAllComentarios());
                contador += 1;
                if(contador == total){ mainActivity.setAlbumsBD(mAllAlbums);}
                return mAllComentarios;
            }
        };
        Future<ArrayList<ComentarioModel>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    private   ArrayList<FotoModel> getFotosDeAlbumDB(String id) throws ExecutionException, InterruptedException {
        Callable<ArrayList<FotoModel>> callable = new Callable<ArrayList<FotoModel>>() {
            @Override
            public ArrayList<FotoModel> call() throws Exception {
                mAllFotos = (ArrayList<FotoModel>) mFotoDao.getFotosDeAlbum(id);
                return mAllFotos;
            }
        };
        Future<ArrayList<FotoModel>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    private  ArrayList<ComentarioModel> getComentariosDeFotoDB(String id) throws ExecutionException, InterruptedException {
        Callable<ArrayList<ComentarioModel>> callable = new Callable<ArrayList<ComentarioModel>>() {
            @Override
            public ArrayList<ComentarioModel> call() throws Exception {
                mAllComentarios.addAll(mComentarioDao.getComentariosDeFoto(id));
                return mAllComentarios;
            }
        };
        Future<ArrayList<ComentarioModel>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }


    public ArrayList<FotoModel> fotosDeAlbum(String id, String owner) throws ExecutionException, InterruptedException {
        //if(isConnected()){ getApiPhotosDeAlbum(id, owner); }
        return getFotosDeAlbumDB(id);
        //if(!isConnected()){ return getFotosDeAlbumDB(id); }
        //else{ return getApiPhotosDeAlbum(id, owner); }
        }

    public ArrayList<ComentarioModel> ComentariosDeFoto(String id) throws ExecutionException, InterruptedException {
        //if(isConnected()){ getApiComent(id); }
        return getComentariosDeFotoDB(id);
        //if(isConnected()){ return getComentariosDeFotoDB(id); }
        //else{ return  getApiComent(id);}
    }

       /* ArrayList<ComentarioModel> comentarios = new ArrayList<>();
        for(int i=0;i<mAllComentarios.size();i++){
            if(mAllFotos.get(i).getAlbumId().compareTo(id)  == 0){
                comentarios.add(mAllComentarios.get(i));
            }
        }
        return comentarios;
        */


    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    private void insertAlbum(AlbumModel album) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mAlbumDao.insert(album);
        });
    }

    private void insertFoto(FotoModel foto) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFotoDao.insert(foto);
        });
    }

    private void insertComentario(ComentarioModel comentario) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mComentarioDao.insert(comentario);
        });
    }

    private void deleteAll(){
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mAlbumDao.deleteAll();
        });
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFotoDao.deleteAll();
        });
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mComentarioDao.deleteAll();
        });
    }

    private void getApiData() {
        //ArrayList<AlbumModel> sets = new ArrayList<AlbumModel>();
        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getList&api_key=84621cc136073e32a85e29c846ff1009&user_id=193998612%40N06&format=json&nojsoncallback=1";
        //text.setText("");
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //text.setText(response.toString());
                        try {

                            JSONObject obj = response.getJSONObject("photosets");
                            //Log.d("Prueba", response.toString());

                            JSONArray jsonarray = obj.optJSONArray("photoset");
                            //Log.d("Almbum", jsonarray.toString());
                            total = jsonarray.length();

                            for(int i=0;i<jsonarray.length();i++){
                                JSONObject object = jsonarray.getJSONObject(i);
                                AlbumModel album = new AlbumModel();
                                album.setId(object.optString("id"));
                                album.setPrimary(object.optLong("primary"));
                                album.setOwner(object.optString("owner"));
                                album.setUsername(object.optString("username"));
                                album.setDate_create(object.optInt("date_create"));
                                JSONObject title = object.optJSONObject("title");
                                album.setTitle(title.optString("_content"));
                                album.setCount_photos(object.optInt("count_photos"));

                                if(firstTime){getApiPhotosDeAlbum(album.getId(), album.getOwner());}
                                //album.setPhoto(getApiPhotos(album.getId(), album.getOwner()));
                                //sets.add(album);
                                mAllAlbums.add(album);
                                insertAlbum(album);
                                //mRepository.insertAlbum(album);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(MainActivity.this, "Error al actualizar albums", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(MainActivity.this, "Error al actualizar albums", Toast.LENGTH_SHORT).show();
            }
        }
        );// Add the request to the RequestQueue.
        MyApplication.getSharedQueue().add(stringRequest);
        //return sets;
    }

    private ArrayList<FotoModel> getApiPhotosDeAlbum(String albumID, String ownerID){
        ArrayList<FotoModel> fotos = new ArrayList<>();
        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=84621cc136073e32a85e29c846ff1009&photoset_id="+albumID+"&user_id="+ownerID+"&format=json&nojsoncallback=1";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Log.d("URL", url);
                            //Log.d("JSON Photo", response.toString());
                            JSONObject obj = response.getJSONObject("photoset");
                            //Log.d("Photo set", obj.toString());
                            JSONArray jsonarray = obj.optJSONArray("photo");
                            //Log.d("Photo List", jsonarray.toString());

                            for(int i=0;i<jsonarray.length();i++){
                                JSONObject object = jsonarray.getJSONObject(i);
                                FotoModel foto = new FotoModel();
                                foto.setId(object.optString("id"));
                                foto.setTitle(object.optString("title"));
                                foto.setServer(object.optString("server"));
                                foto.setSecret(object.optString("secret"));
                                foto.setWebUrl("https://www.flickr.com/photos/"+ownerID+"/"+foto.getId());
                                foto.setImageUrl("https://live.staticflickr.com/"+foto.getServer()+"/"+foto.getId()+"_"+foto.getSecret()+"_w.jpg");

                                if(firstTime){getApiComent(foto.getId());}
                                //foto.setComentarios(getApiComent(foto));
                                foto.setAlbumId(albumID);

                                fotos.add(foto);
                                mAllFotos.add(foto);
                                insertFoto(foto);

                                //mRepository.insertFoto(foto);
                            }
                            if(firstTime){
                                contador += 1;
                                if (contador == total){
                                    firstTime = false;
                                    mainActivity.setAlbums(mAllAlbums);
                                    //Log.d("Cantidad albums", String.valueOf(mAllAlbums.size()));
                                    //Log.d("Cantidad fotos", String.valueOf(mAllFotos.size()));
                                    //Log.d("Cantidad comentarios", String.valueOf(mAllComentarios.size()));
                                }
                            }
                            else if(secondActivity != null){
                                secondActivity.actualizarUI(fotos);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            //Log.d("CATCH ERROR", e.toString());
                            //Toast.makeText(MainActivity.this, "Error al actualizar fotos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(MainActivity.this, "Error al actualizar fotos", Toast.LENGTH_SHORT).show();
                //Log.d("Volley ERROR", error.getMessage());

            }
        }
        );// Add the request to the RequestQueue.
        MyApplication.getSharedQueue().add(stringRequest);
        return fotos;
    }

    private ArrayList<ComentarioModel> getApiComent(String id) {
        ArrayList<ComentarioModel> comments = new ArrayList<ComentarioModel>();

        String url = "https://www.flickr.com/services/rest/?method=flickr.photos.comments.getList&api_key=84621cc136073e32a85e29c846ff1009&photo_id="+id+"&format=json&nojsoncallback=1";
        //Log.d("Url comentario", url);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject obj = response.getJSONObject("comments");
                            //Log.d("Prueba", response.toString());

                            JSONArray jsonarray = obj.optJSONArray("comment");
                            if(jsonarray!=null) {
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject object = jsonarray.getJSONObject(i);
                                    ComentarioModel comentario = new ComentarioModel();

                                    comentario.setRealname(object.optString("realname"));
                                    comentario.set_content(object.optString("_content"));
                                    comentario.setId(object.optString("id"));
                                    comentario.setFotoId(id);

                                    comments.add(comentario);
                                    mAllComentarios.add(comentario);
                                    insertComentario(comentario);

                                    if(thirdActivity != null){
                                        thirdActivity.actualizarComentarios(comments);
                                    }

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(MainActivity.this, "Error al actualizar comentarios", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(MainActivity.this, "Error al actualizar comentarios", Toast.LENGTH_SHORT).show();
            }
        }
        );// Add the request to the RequestQueue.
        MyApplication.getSharedQueue().add(stringRequest);
        return comments;
    }
}
