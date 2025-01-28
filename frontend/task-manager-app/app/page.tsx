// frontend/task-manager-app/app/page.tsx
'use client';

import { Amplify } from 'aws-amplify';
import { authConfig } from '../amplify/auth';
import { Authenticator } from '@aws-amplify/ui-react';
import '@aws-amplify/ui-react/styles.css';


// // Configure Amplify

Amplify.configure({
  Auth: {
    Cognito: {
      userPoolId: "ap-south-1_Y40XcudCO",
      userPoolClientId: "465avat7odepm0rbjg5qftr3rh",
      identityPoolId: "ap-south-1:6c2f1370-ee81-4516-a61e-41687399162c",
      loginWith: {
        email: true,
      },
      signUpVerificationMethod: "code",
      userAttributes: {
        email: {
          required: true,
        },
      },
      allowGuestAccess: true,
      passwordFormat: {
        minLength: 8,
        requireLowercase: true,
        requireUppercase: true,
        requireNumbers: true,
        requireSpecialCharacters: true,
      },
    },
  },
});

export default function Home() {
  return (
    <Authenticator initialState="signIn">
      {({ signOut, user }) => (
        <div>
          <h1>Task Manager App</h1>
          <p>Welcome, {user?.username}!</p>
          <button onClick={signOut}>Sign Out</button>
        </div>
      )}
    </Authenticator>
  );
}