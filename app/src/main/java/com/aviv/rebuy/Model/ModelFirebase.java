package com.aviv.rebuy.Model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;

import com.aviv.rebuy.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ModelFirebase {

//Product

    public interface GetAllProductsListener{
        void onComplete(List<Product> list);
    }

    public void getAllProducts(Long lastUpdated, final GetAllProductsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(lastUpdated, 0);
        db.collection("products").whereGreaterThanOrEqualTo("lastUpdated", ts).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Product> data = new LinkedList<Product>();
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        Product pr = new Product();
                        pr.fromMap(doc.getData());
                        //Student st = doc.toObject(Student.class);
                        data.add(pr);
                        Log.d("TAG", "st: " + pr.getId());
                    }
                }
                listener.onComplete(data);
            }
        });
    }

    public void getAllProducts(final GetAllProductsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products").get().addOnCompleteListener(task -> {
            List<Product> data = new ArrayList<>();
            if (task.isSuccessful()) {
                for (DocumentSnapshot doc : task.getResult()) {
                    Product product = new Product();
                    product.fromMap(doc.getData());
                    data.add(product);
                }
            }
            listener.onComplete(data);
        });
    }

    public void addProduct(Product product, final Model.AddProductListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("products").document(product.getId())
                .set(product.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","product added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","fail adding product");
                listener.onComplete();
            }
        });
    }
    public void updateProduct(Product product, Model.AddProductListener listener) {
        addProduct(product,listener);
    }


    public void getProduct(String id, final Model.GetProductListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Product product = null;
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        product = new Product();
                        product.fromMap(task.getResult().getData());
                    }
                }
                listener.onComplete(product);
            }
        });
    }

    public void getUser(String id, final Model.GetUserListener listener) {
        FirebaseFirestore.getInstance().collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = null;
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        user = new User();
                        if(user!=null) user.fromMap(task.getResult().getData());
                    }
                }
                listener.onComplete(user);
            }
        });
    }


    public void delete(Product product, final Model.DeleteListener listener) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("students").document(student.getId())
//                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                listener.onComplete();
//            }
//        });
    }



    public void uploadImage(Bitmap imageBmp, String name, final Model.UploadImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }

//User

    public interface GetAllUsersListener{ void onComplete(List<User> list);}
    public void getAllUsers(final GetAllUsersListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").get().addOnCompleteListener(task -> {
            List<User> data = new ArrayList<>();
            if (task.isSuccessful()) {
                for (DocumentSnapshot doc : task.getResult()) {
                    User user = new User();
                    user.fromMap(doc.getData());
                    data.add(user);
                }
            }
            listener.onComplete(data);
        });
    }

    public void addUser(User user, final Model.AddUserListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getId())
                .set(user.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","user added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","fail adding student");
                listener.onComplete();
            }
        });
    }

    }






















