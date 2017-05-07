// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.IBinder;
import android.os.Binder;
import android.os.RemoteException;
import android.os.IInterface;

public interface de extends IInterface
{
    void a(final dd p0) throws RemoteException;
    
    void a(final dd p0, final int p1) throws RemoteException;
    
    void a(final dd p0, final int p1, final String p2, final byte[] p3) throws RemoteException;
    
    void a(final dd p0, final int p1, final byte[] p2) throws RemoteException;
    
    void b(final dd p0) throws RemoteException;
    
    void b(final dd p0, final int p1) throws RemoteException;
    
    void c(final dd p0) throws RemoteException;
    
    int getMaxNumKeys() throws RemoteException;
    
    int getMaxStateSize() throws RemoteException;
    
    public abstract static class a extends Binder implements de
    {
        public static de t(final IBinder binder) {
            if (binder == null) {
                return null;
            }
            final IInterface queryLocalInterface = binder.queryLocalInterface("com.google.android.gms.appstate.internal.IAppStateService");
            if (queryLocalInterface != null && queryLocalInterface instanceof de) {
                return (de)queryLocalInterface;
            }
            return new de.a.a(binder);
        }
        
        public boolean onTransact(int n, final Parcel parcel, final Parcel parcel2, final int n2) throws RemoteException {
            switch (n) {
                default: {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.appstate.internal.IAppStateService");
                    return true;
                }
                case 5001: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    n = this.getMaxStateSize();
                    parcel2.writeNoException();
                    parcel2.writeInt(n);
                    return true;
                }
                case 5002: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    n = this.getMaxNumKeys();
                    parcel2.writeNoException();
                    parcel2.writeInt(n);
                    return true;
                }
                case 5003: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    this.a(dd.a.s(parcel.readStrongBinder()), parcel.readInt(), parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 5004: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    this.a(dd.a.s(parcel.readStrongBinder()), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 5005: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    this.a(dd.a.s(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 5006: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    this.a(dd.a.s(parcel.readStrongBinder()), parcel.readInt(), parcel.readString(), parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 5007: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    this.b(dd.a.s(parcel.readStrongBinder()), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 5008: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    this.b(dd.a.s(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 5009: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    this.c(dd.a.s(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
            }
        }
        
        private static class a implements de
        {
            private IBinder dU;
            
            a(final IBinder du) {
                this.dU = du;
            }
            
            @Override
            public void a(final dd dd) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    IBinder binder;
                    if (dd != null) {
                        binder = dd.asBinder();
                    }
                    else {
                        binder = null;
                    }
                    obtain.writeStrongBinder(binder);
                    this.dU.transact(5005, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void a(final dd dd, final int n) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    IBinder binder;
                    if (dd != null) {
                        binder = dd.asBinder();
                    }
                    else {
                        binder = null;
                    }
                    obtain.writeStrongBinder(binder);
                    obtain.writeInt(n);
                    this.dU.transact(5004, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void a(final dd dd, final int n, final String s, final byte[] array) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    IBinder binder;
                    if (dd != null) {
                        binder = dd.asBinder();
                    }
                    else {
                        binder = null;
                    }
                    obtain.writeStrongBinder(binder);
                    obtain.writeInt(n);
                    obtain.writeString(s);
                    obtain.writeByteArray(array);
                    this.dU.transact(5006, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void a(final dd dd, final int n, final byte[] array) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    IBinder binder;
                    if (dd != null) {
                        binder = dd.asBinder();
                    }
                    else {
                        binder = null;
                    }
                    obtain.writeStrongBinder(binder);
                    obtain.writeInt(n);
                    obtain.writeByteArray(array);
                    this.dU.transact(5003, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            public IBinder asBinder() {
                return this.dU;
            }
            
            @Override
            public void b(final dd dd) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    IBinder binder;
                    if (dd != null) {
                        binder = dd.asBinder();
                    }
                    else {
                        binder = null;
                    }
                    obtain.writeStrongBinder(binder);
                    this.dU.transact(5008, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void b(final dd dd, final int n) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    IBinder binder;
                    if (dd != null) {
                        binder = dd.asBinder();
                    }
                    else {
                        binder = null;
                    }
                    obtain.writeStrongBinder(binder);
                    obtain.writeInt(n);
                    this.dU.transact(5007, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void c(final dd dd) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    IBinder binder;
                    if (dd != null) {
                        binder = dd.asBinder();
                    }
                    else {
                        binder = null;
                    }
                    obtain.writeStrongBinder(binder);
                    this.dU.transact(5009, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public int getMaxNumKeys() throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    this.dU.transact(5002, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public int getMaxStateSize() throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    this.dU.transact(5001, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}