// frontend/task-manager-app/app/page.tsx
'use client';

import { Amplify } from 'aws-amplify';
import { authConfig,configureAuth } from '../amplify/auth';
import { Authenticator } from '@aws-amplify/ui-react';
import '@aws-amplify/ui-react/styles.css';


// Configure Amplify
configureAuth();


export default function Home() {
  return (
    <Authenticator
      initialState="signIn"
      components={{
        SignUp: {
          FormFields() {
            return (
              <>
                {/* Keep the default fields */}
                <Authenticator.SignUp.FormFields />
                
                {/* Add just the missing address field */}
                <div>
                  <label>Address</label>
                  <input
                    type="text"
                    name="address"
                    placeholder="Enter your address"
                    required
                  />
                </div>
              </>
            );
          },
        },
      }}
    >
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