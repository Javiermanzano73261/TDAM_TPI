package com.example.integradortdam;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

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
        if(obtenerData()){
            Toast.makeText(mainActivity, mainActivity.getString(R.string.updateMessage), Toast.LENGTH_LONG ).show();
        }
        else{
            Toast.makeText(mainActivity, mainActivity.getString(R.string.errorUpdateMessage), Toast.LENGTH_LONG ).show();
        };
    }

    AppRepository(Application application)  {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        mAlbumDao = db.albumDao();
        mFotoDao = db.fotoDao();
        mComentarioDao = db.comentarioDao();
    }

    public void setMainActivity(MainActivity mainActivity) { this.mainActivity = mainActivity; }

    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) application.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public boolean obtenerData() {
        boolean seLlamoAApi = false;
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
            getApiData();
            seLlamoAApi = true;
        }
        return seLlamoAApi;
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

    public ArrayList<FotoModel> getFotosDeAlbumDB(String id) throws ExecutionException, InterruptedException {
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

    public  ArrayList<ComentarioModel> getComentariosDeFotoDB(String id) throws ExecutionException, InterruptedException {
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
        ArrayList<AlbumModel> albums = new ArrayList<>();
        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getList&api_key=003fa3879559a46bee84d99f01192d46&user_id=193998612%40N06&format=json&nojsoncallback=1";
        //text.setText("");
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("photosets");
                            JSONArray jsonarray = obj.optJSONArray("photoset");
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
                                albums.add(album);
                                insertAlbum(album);
                                if(contador==total){mAllAlbums=albums;}

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
    }

    private void getApiPhotosDeAlbum(String albumID, String ownerID){
        ArrayList<FotoModel> fotos = new ArrayList<>();
        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=003fa3879559a46bee84d99f01192d46&photoset_id="+albumID+"&user_id="+ownerID+"&format=json&nojsoncallback=1";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("photoset");
                            JSONArray jsonarray = obj.optJSONArray("photo");

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
                                foto.setAlbumId(albumID);
                                fotos.add(foto);
                                mAllFotos.add(foto);
                                insertFoto(foto);
                            }
                            if(firstTime){
                                contador += 1;
                                if (contador == total){
                                    firstTime = false;
                                    mainActivity.setAlbums(mAllAlbums);
                                }
                            }

                        } catch (Exception e) { e.printStackTrace();
                            //Toast.makeText(MainActivity.this, "Error al actualizar fotos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(MainActivity.this, "Error al actualizar fotos", Toast.LENGTH_SHORT).show();
            }
        }
        );// Add the request to the RequestQueue.
        MyApplication.getSharedQueue().add(stringRequest);

    }

    private void getApiComent(String id) {
        ArrayList<ComentarioModel> comments = new ArrayList<ComentarioModel>();
        String url = "https://www.flickr.com/services/rest/?method=flickr.photos.comments.getList&api_key=003fa3879559a46bee84d99f01192d46&photo_id="+id+"&format=json&nojsoncallback=1";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("comments");
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
    }
}
