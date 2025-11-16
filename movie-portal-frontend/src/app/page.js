import { redirect } from 'next/navigation';

export default function HomePage() {
  // This server component will immediately redirect any request
  // from the root URL ("/") to the "/login" page.
  redirect('/login');

  // No content is rendered because the redirection happens on the server.
  return null;
}
