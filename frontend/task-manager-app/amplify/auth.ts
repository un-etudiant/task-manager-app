// ../amplify/auth.ts
// import { type AuthConfig } from '@aws-amplify/core';

// export const authConfig: AuthConfig = {
//   Cognito: {
//     userPoolId: 'ap-south-1_Y40XcudCO',
//     userPoolClientId: '465avat7odepm0rbjg5qftr3rh',
//     signInWithRedirect: true,  // Add this if using OAuth
//     oauth: {
//       domain: 'ap-south-1y40xcudco.auth.ap-south-1.amazoncognito.com',
//       scopes: ['openid', 'profile', 'email'],
//       redirectSignIn: ['http://localhost:3000'],
//       redirectSignOut: ['http://localhost:3000'],
//       responseType: 'code' as const
//     }
//   }
// };
import { Amplify } from "aws-amplify"
export const authConfig= 
{
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
}