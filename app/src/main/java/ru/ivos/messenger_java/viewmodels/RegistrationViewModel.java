package ru.ivos.messenger_java.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ru.ivos.messenger_java.entities.User;

public class RegistrationViewModel extends ViewModel {

    private static FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference usersRef;

    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public RegistrationViewModel() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    user.setValue(firebaseAuth.getCurrentUser());
                }
            }
        });

        db = FirebaseDatabase.getInstance();
        usersRef = db.getReference("Users");
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void signUp(
            String email,
            String password,
            String name,
            String secondName,
            int age
    ) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser fbUser = authResult.getUser();
                        if (fbUser == null) {
                            return;
                        }
                        User newUser = new User(
                                fbUser.getUid(),
                                name,
                                secondName,
                                age,
                                false
                        );
                        usersRef.child(newUser.getId()).setValue(newUser);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        error.setValue(e.getMessage());
                    }
                });
    }
}
