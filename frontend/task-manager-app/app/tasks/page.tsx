// frontend/task-manager-app/app/tasks/page.tsx
'use client';

import { useAuthenticator } from '@aws-amplify/ui-react';
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';

export default function TasksPage() {
  const { authStatus } = useAuthenticator((context) => [context.authStatus]);
  const router = useRouter();

  useEffect(() => {
    if (authStatus !== 'authenticated') {
      router.push('/'); // Redirect to home page if not authenticated
    }
  }, [authStatus, router]);

  if (authStatus !== 'authenticated') {
    return null; // Show nothing while redirecting
  }

  return (
    <div>
      <h1>Tasks</h1>
      <p>This page is protected. Only authenticated users can access it.</p>
    </div>
  );
}