package com.goldze.mvvmhabit.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.goldze.mvvmhabit.data.DemoRepository;
import com.goldze.mvvmhabit.ui.login.LoginViewModel;
import com.goldze.mvvmhabit.ui.network.NetWorkViewModel;

/**
 * Created by goldze on 2019/3/26.
 */
public class AppViewModelFactory extends ViewModelProvider.NewInstanceFactory {
 @SuppressLint("StaticFieldLeak")
 private static volatile AppViewModelFactory INSTANCE;
 private final Application mApplication;
 private final DemoRepository mRepository;

 private AppViewModelFactory(Application application, DemoRepository repository) {
  this.mApplication = application;
  this.mRepository = repository;
 }

 public static AppViewModelFactory getInstance(Application application) {
  if (INSTANCE == null) {
   synchronized (AppViewModelFactory.class) {
    if (INSTANCE == null) {
     INSTANCE = new AppViewModelFactory(application, Injection.provideDemoRepository());
    }
   }
  }
  return INSTANCE;
 }

 @VisibleForTesting
 public static void destroyInstance() {
  INSTANCE = null;
 }

 @NonNull
 @Override
 public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
  if (modelClass.isAssignableFrom(NetWorkViewModel.class)) {
   return (T) new NetWorkViewModel(mApplication, mRepository);
  } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
   return (T) new LoginViewModel(mApplication, mRepository);
  }
  throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
 }
}
